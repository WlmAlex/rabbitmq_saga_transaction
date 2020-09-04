package com.learn.rabbitmq_saga.customer_room.entities;

public class Room {

    private int roomId;
    private int remainingSeats;

    public Room(int roomId, int remainingSeats) {
        this.roomId = roomId;
        this.remainingSeats = remainingSeats;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }
}
