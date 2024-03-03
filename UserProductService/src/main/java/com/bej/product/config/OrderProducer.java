package com.bej.product.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class OrderProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/sent-order")
    public String sendNotification(@RequestBody OrderDTO orderDTO){
        orderDTO.setOrderId(UUID.randomUUID().toString());
        orderDTO.setOrderDate(new Date());
        rabbitTemplate.convertAndSend(OrderConfig.EXCHANGE, OrderConfig.ROUTING_KEY, orderDTO);
        return "Order Details Published";
    }
}
