package com.example.customer_ticket.services;

import com.example.customer_ticket.config.BrokerConfiguration;
import com.example.customer_ticket.dao.TicketRepository;
import com.example.customer_ticket.entity.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.learn.rabbitmq_saga.saga_common.core.Message;
import com.learn.rabbitmq_saga.saga_common.dtos.BookingTicketDTO;
import com.learn.rabbitmq_saga.saga_common.util.SagaConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RabbitTemplate template;

    public boolean reserveTicket(int userId, int roomId, int reverseSeatsNum) {
        String routeKey = BrokerConfiguration.r1;
        String exchangeName = BrokerConfiguration.e1;

        double ticketCost = 10.0;
        BookingTicketDTO dto = new BookingTicketDTO(userId, roomId, ticketCost, reverseSeatsNum);

        String content = null;
        try {
            content = SagaConverter.encode(dto);
        } catch (IOException e) {
            System.err.println("convert error");
            return false;
        }

        Message message = new Message(content, Message.Command.BOOK_TICKET, routeKey);
        Message result = (Message) template.convertSendAndReceive(exchangeName, routeKey, message);

        if (result == null || !result.isDone()) {
            return false;
        }
        Ticket ticket = new Ticket(userId, roomId);
        ticketRepository.save(ticket);
        return true;
    }
}
