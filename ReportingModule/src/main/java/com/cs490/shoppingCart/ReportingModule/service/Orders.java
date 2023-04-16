package com.cs490.shoppingCart.ReportingModule.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Orders {

    private List<OrderDTO> orders = new ArrayList<>();

    public Orders(){}

    public List<OrderDTO> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
