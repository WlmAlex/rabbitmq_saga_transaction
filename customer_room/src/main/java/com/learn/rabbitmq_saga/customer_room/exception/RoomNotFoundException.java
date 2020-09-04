package com.learn.rabbitmq_saga.customer_room.exception;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String message) {
        super(message);
    }
}
