package com.cs490.shoppingCart.NotificationModule.service;

import java.util.Date;

public class OrderDTO {
    private String order_id;
    private String user_id;
    private double total_price;
    private Date order_date;
    private String order_status;

    public OrderDTO(String order_id, String user_id, double total_price, Date order_date, String order_status) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.order_date = order_date;
        this.order_status = order_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
