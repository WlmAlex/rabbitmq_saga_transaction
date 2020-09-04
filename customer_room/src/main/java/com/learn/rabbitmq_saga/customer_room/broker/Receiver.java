package com.learn.rabbitmq_saga.customer_room.broker;

import com.learn.rabbitmq_saga.customer_room.exception.InsufficientSeatsException;
import com.learn.rabbitmq_saga.customer_room.exception.RoomNotFoundException;
import com.learn.rabbitmq_saga.customer_room.services.RoomService;
import com.learn.rabbitmq_saga.saga_common.core.Message;
import com.learn.rabbitmq_saga.saga_common.dtos.BookingTicketDTO;
import com.learn.rabbitmq_saga.saga_common.util.SagaConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {

    @Autowired
    private RoomService service;

    //question: if there have 2 modules of customer_room, how to decide which module to execute the request?
    @RabbitListener(queues = "#{queueA.name}")
    public Message executor(Message message) throws IOException {
        System.err.println("Received id: " + message.getId());
        BookingTicketDTO ticketDTO = SagaConverter.decode(message.getContent(), BookingTicketDTO.class);
        if (message.getCommand() == Message.Command.RESERVE_SEAT) {
            try {
                service.reserveSeat(ticketDTO.getRoomId(), ticketDTO.getReserveSeatsNum());
                message.setDone(true);
            } catch (InsufficientSeatsException | RoomNotFoundException e) {
                e.printStackTrace();
            }
        } else if (message.getCommand() == Message.Command.RESERVE_SEAT_ROLLBACK) {
            try {
                service.cancelReserveSeat(ticketDTO.getRoomId(), ticketDTO.getReserveSeatsNum());
                message.setDone(true);
                System.err.println("Cancel reserve seat successfully");
            } catch (RoomNotFoundException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}