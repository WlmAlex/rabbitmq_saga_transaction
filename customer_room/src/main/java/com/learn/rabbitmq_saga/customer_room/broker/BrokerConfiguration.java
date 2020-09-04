package com.learn.rabbitmq_saga.customer_room.broker;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfiguration {

    @Bean
    public Queue queueA() {
        return new AnonymousQueue();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("my_service_router");
    }

    @Bean
    public Binding binding(Queue queueA, DirectExchange directExchange) {
        return BindingBuilder.bind(queueA).to(directExchange).with("room_route");
    }

    //we can delete this cause we are not using jackson converter here
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}