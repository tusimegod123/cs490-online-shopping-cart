package com.cs490.shoppingCart.OrderProcessingModule.dto;

import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class OrderList {

    private List<OrderDTO> orders = new ArrayList<>();

    public OrderList() {
    }

    public List<OrderDTO> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

}
