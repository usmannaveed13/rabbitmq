package com.example.rabbitmqspringboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqSpringBootApplication {

	@Value("${spring.rabbitmq.host}")
	private String HOST;

	@Value("${spring.rabbitmq.port}")
	private int PORT;

	@Value("${spring.rabbitmq.virtual-host}")
	private String VIRTUAL_HOST;

	@Value("${spring.rabbitmq.username}")
	private String USERNAME;

	@Value("${spring.rabbitmq.password}")
	private String PASSWORD;

	@Bean
	public ConnectionFactory connectionFactory()
	{
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setUsername(USERNAME);
		connectionFactory.setPassword(PASSWORD);
		connectionFactory.setPort(PORT);
		connectionFactory.setHost(HOST);
		connectionFactory.setVirtualHost(VIRTUAL_HOST);

		return connectionFactory.getRabbitConnectionFactory();
	}

	@Bean
	public MessageConverter messageConverter()
	{
		ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqSpringBootApplication.class, args);
	}

}
