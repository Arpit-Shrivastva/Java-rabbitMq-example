package com.example.ConsumeNotification;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    @RabbitListener(queuesToDeclare = @Queue("order_Queue"))
    public void orderConsume(OrderDTO orderDTO){
        System.out.println(orderDTO);
    }
}
