package com.learn.rabbitmq_saga.customer_account.exception;

public class InsufficientBudgetException extends Exception {

    private static final long serialId = 1L;

    public InsufficientBudgetException(String message) {
        super(message);
    }
}
