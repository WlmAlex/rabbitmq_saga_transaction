package com.example.customer_ticket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfiguration {

    /**** QUEUE CONFIGURATION ***/
    public static final String q1 = "Q1";    // queue name
    public static final String e1 = "E1";    // exchange name
    public static final String r1 = "R1"; // route key


    @Bean
    public Queue queue() {
        return new Queue(q1, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(e1);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(r1);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setQueueNames(queue().getName());
        container.setConnectionFactory(connectionFactory);
        //in order to debug, set reply timeout to 50s
        return container;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}