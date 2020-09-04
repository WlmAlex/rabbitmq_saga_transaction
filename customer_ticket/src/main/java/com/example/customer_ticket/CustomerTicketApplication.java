package com.example.customer_ticket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = "com.example.customer_ticket")
@SpringBootApplication
public class CustomerTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerTicketApplication.class, args);
    }

}
