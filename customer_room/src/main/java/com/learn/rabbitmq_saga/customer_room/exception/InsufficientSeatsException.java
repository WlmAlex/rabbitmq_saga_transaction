package com.learn.rabbitmq_saga.customer_room.exception;

public class InsufficientSeatsException extends Exception {
    public InsufficientSeatsException(String message) {
        super(message);
    }
}
