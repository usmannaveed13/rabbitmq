package com.example.rabbitmqspringboot.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DirectExchangeConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${rabbitmq.direct.queue-1}")
    private String DIRECT_QUEUE_1;

    @Value("${rabbitmq.direct.queue-2}")
    private String DIRECT_QUEUE_2;

    @Value("${rabbitmq.direct.exchange}")
    private String DIRECT_EXCHANGE;

    @Value("${rabbitmq.direct.routing-key-1}")
    private String DIRECT_ROUTING_KEY_1;

    @Value("${rabbitmq.direct.routing-key-2}")
    private String DIRECT_ROUTING_KEY_2;

    Queue createDirectQueue1()
    {
        return new Queue(DIRECT_QUEUE_1, true, false, false);
    }

    Queue createDirectQueue2()
    {
        return new Queue(DIRECT_QUEUE_2, true, false, false);
    }

    DirectExchange createDirectExchange()
    {
        return new DirectExchange(DIRECT_EXCHANGE, true, false);
    }

    Binding createDirectBinding1()
    {
        return BindingBuilder.bind(createDirectQueue1()).to(createDirectExchange()).with(DIRECT_ROUTING_KEY_1);
    }

    Binding createDirectBinding2()
    {
        return BindingBuilder.bind(createDirectQueue2()).to(createDirectExchange()).with(DIRECT_ROUTING_KEY_2);
    }

    @Bean
    public AmqpTemplate directExchange(ConnectionFactory connectionFactory, MessageConverter messageConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(DIRECT_EXCHANGE);
        return rabbitTemplate;
    }

    @PostConstruct
    public void init()
    {
        amqpAdmin.declareQueue(createDirectQueue1());
        amqpAdmin.declareQueue(createDirectQueue2());
        amqpAdmin.declareExchange(createDirectExchange());
        amqpAdmin.declareBinding(createDirectBinding1());
        amqpAdmin.declareBinding(createDirectBinding2());
    }



}
