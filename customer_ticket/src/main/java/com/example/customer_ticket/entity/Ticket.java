package com.example.customer_ticket.entity;

import java.util.Date;

public class Ticket {

    private int ticketId;

    private int userId;

    private int roomId;

    private Date issuedDate;

    public Ticket(int userId, int roomId) {
        this.userId = userId;
        this.roomId = roomId;
        this.issuedDate = new Date();
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
}
