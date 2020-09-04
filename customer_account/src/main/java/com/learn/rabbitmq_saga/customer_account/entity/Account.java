package com.learn.rabbitmq_saga.customer_account.entity;

public class Account {

    private int accountId;
    private int userId;
    private double deposit;

    public Account() {
    }

    public Account(int userId, double deposit) {
        this.userId = userId;
        this.deposit = deposit;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
}