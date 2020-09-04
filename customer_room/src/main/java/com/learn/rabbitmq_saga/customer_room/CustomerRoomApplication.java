package com.learn.rabbitmq_saga.customer_room;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.learn.rabbitmq_saga.customer_room")
@SpringBootApplication
public class CustomerRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerRoomApplication.class, args);
    }

}
