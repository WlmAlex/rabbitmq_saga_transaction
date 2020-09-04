package com.learn.rabbitmq_saga.customer_room.dao;

import com.learn.rabbitmq_saga.customer_room.entities.Room;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomRepository {

    Room findRoomById(int roomId);

    void save(Room room);
}