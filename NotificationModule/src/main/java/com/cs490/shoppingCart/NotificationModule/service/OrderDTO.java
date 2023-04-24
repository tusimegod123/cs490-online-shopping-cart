package com.cs490.shoppingCart.NotificationModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String order_id;
    private String user_id;
    private double total_price;
    private Date order_date;
    private String order_status;
}
