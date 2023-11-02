package com.example.rabbitmqspringboot.consumer;

import com.example.rabbitmqspringboot.model.QueueObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerConsumer {

    @RabbitListener(queues = {"${rabbitmq.fanout.queue-1}","${rabbitmq.fanout.queue-2}" },
            containerFactory = "listenerContainerFactory")
    public void receiveMessages(QueueObject object)
    {
      System.out.println(object);
    }
}
