package com.learn.rabbitmq_saga.customer_account.config;

import com.learn.rabbitmq_saga.customer_account.exception.InsufficientBudgetException;
import com.learn.rabbitmq_saga.customer_account.service.BudgetService;
import com.learn.rabbitmq_saga.saga_common.core.Message;
import com.learn.rabbitmq_saga.saga_common.dtos.BookingTicketDTO;
import com.learn.rabbitmq_saga.saga_common.util.SagaConverter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;

@Component
public class Receiver {

    @Autowired
    private BudgetService service;

    @RabbitListener(queues = "#{queueB.name}")
    public Message executor(Message message) throws IOException {
        System.err.println("Received command: " + message.getId());
        BookingTicketDTO ticketDto = SagaConverter.decode(message.getContent(), BookingTicketDTO.class);
        if (message.getCommand() == Message.Command.MAKE_PAYMENT) {

            try {
                service.withDrawn(ticketDto.getUserId(), ticketDto.getTicketCost());
                message.setDone(true);
                System.err.println("Make payment successfully: " + ticketDto.getTicketCost());
            } catch (AccountNotFoundException | InsufficientBudgetException e) {
                e.printStackTrace();
            }
        } else if (message.getCommand() == Message.Command.MAKE_PAYMENT_ROLLBACK) {
            try {
                service.deposit(ticketDto.getUserId(), ticketDto.getTicketCost());
                message.setDone(true);
                System.err.println("Payment rollback successfully: " + ticketDto.getTicketCost());
            } catch (AccountNotFoundException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}