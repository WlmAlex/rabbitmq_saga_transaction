package com.learn.rabbitmq_saga.orchestration.config;

import com.learn.rabbitmq_saga.orchestration.services.OrchestrationService;
import com.learn.rabbitmq_saga.saga_common.core.Message;
import com.learn.rabbitmq_saga.saga_common.dtos.BookingTicketDTO;
import com.learn.rabbitmq_saga.saga_common.util.SagaConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Stack;

@Component
public class OrchestrationReceiver {

    @Autowired
    private OrchestrationService service;

    @RabbitListener(queues = "Q1")
    public Message buyTicketTransaction(Message message) throws IOException {
        System.err.println("Received id: " + message.getId());

        Stack<Message> tasks = new Stack<>();

        if (message.getCommand() == Message.Command.BOOK_TICKET) {

            BookingTicketDTO ticketDTO = SagaConverter.decode(message.getContent(), BookingTicketDTO.class);

            /**
             * DEFINE TRANSACTION
             */
            Message[] taskPrep = new Message[]{
                    new Message(message.getContent(), Message.Command.MAKE_PAYMENT, "account_route"),
                    new Message(message.getContent(), Message.Command.RESERVE_SEAT, "room_route")
            };

            for (Message msg : taskPrep) {
                tasks.push(msg);
            }
        }

        service.tracker(tasks, message);
        return message;
    }
}
