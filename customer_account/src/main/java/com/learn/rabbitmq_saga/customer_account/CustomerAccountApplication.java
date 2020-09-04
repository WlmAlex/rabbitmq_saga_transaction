package com.learn.rabbitmq_saga.customer_account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(value = "com.learn.rabbitmq_saga.customer_account")
@SpringBootApplication
public class CustomerAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerAccountApplication.class, args);
    }

}