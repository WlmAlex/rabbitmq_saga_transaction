package com.learn.rabbitmq_saga.saga_common.dtos;

public class BookingTicketDTO {
    private int userId;
    private double ticketCost;
    private int roomId;
    private int reserveSeatsNum;

    public BookingTicketDTO() {
    }

    public BookingTicketDTO(int userId, int roomId, double ticketCost, int reverseSeatsNum) {
        this.userId = userId;
        this.roomId = roomId;
        this.ticketCost = ticketCost;
        this.reserveSeatsNum = reverseSeatsNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(double ticketCost) {
        this.ticketCost = ticketCost;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getReserveSeatsNum() {
        return reserveSeatsNum;
    }

    public void setReserveSeatsNum(int reserveSeatsNum) {
        this.reserveSeatsNum = reserveSeatsNum;
    }
}
