package com.example.rabbitmqspringboot.controller;

import com.example.rabbitmqspringboot.model.QueueObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TopicController {

    @Autowired
    private AmqpTemplate topicExchange;

    @PostMapping("topic/{key}")
    public ResponseEntity<?> sendMessageWithTopicExchange(@PathVariable String key)
    {
        QueueObject object = new QueueObject("topic", LocalDateTime.now());
        topicExchange.convertAndSend(key, object);

        return ResponseEntity.ok(true);
    }

}
