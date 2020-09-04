package com.learn.rabbitmq_saga.customer_account.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("my_service_router");
    }

    @Bean
    public Queue queueB() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with("account_route");
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}