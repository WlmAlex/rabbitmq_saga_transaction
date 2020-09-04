package com.learn.rabbitmq_saga.customer_room.services;

import com.learn.rabbitmq_saga.customer_room.dao.RoomRepository;
import com.learn.rabbitmq_saga.customer_room.entities.Room;
import com.learn.rabbitmq_saga.customer_room.exception.InsufficientSeatsException;
import com.learn.rabbitmq_saga.customer_room.exception.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public void reserveSeat(int roomId, int reserveSeatsNum) throws InsufficientSeatsException, RoomNotFoundException {
        if (!hasSeat(roomId, reserveSeatsNum)) {
            throw new InsufficientSeatsException("Not enough seats!!!");
        }

        Room room = roomRepository.findRoomById(roomId);
        int remainingSeats = room.getRemainingSeats() - reserveSeatsNum;
        room.setRemainingSeats(remainingSeats);
        roomRepository.save(room);
    }

    private boolean hasSeat(int roomId, int reserveSeatsNum) throws RoomNotFoundException {
        Room seat = roomRepository.findRoomById(roomId);
        if (seat == null) {
            throw new RoomNotFoundException("Room not found!!!");
        }
        return seat.getRemainingSeats() >= reserveSeatsNum;
    }

    public void cancelReserveSeat(int roomId, int reserveSeatsNum) throws RoomNotFoundException {


        Room room = roomRepository.findRoomById(roomId);
        if (room == null) {
            throw new RoomNotFoundException("Room not found!!!");
        }
        int remainingSeats = room.getRemainingSeats() + reserveSeatsNum;
        room.setRemainingSeats(remainingSeats);
        roomRepository.save(room);
    }
}