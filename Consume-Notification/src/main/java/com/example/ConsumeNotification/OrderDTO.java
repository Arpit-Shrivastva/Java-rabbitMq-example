package com.example.ConsumeNotification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderDTO {
    private String orderId;
    private Date orderDate;
    private String productName;
    private int quantity;
    private int price;
}
