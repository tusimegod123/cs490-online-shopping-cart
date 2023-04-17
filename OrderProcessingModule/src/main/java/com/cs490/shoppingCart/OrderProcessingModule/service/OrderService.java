package com.cs490.shoppingCart.OrderProcessingModule.service;


import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderRequestDTO;
import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
import com.cs490.shoppingCart.OrderProcessingModule.dto.GuestOrderRequest;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

     Order createOrder(OrderRequestDTO orderRequestDTO);



    boolean checkOrderExistance(Integer id);


    Order getOrder(int orderId);

    Order createGuestOrder(GuestOrderRequest guestOrderRequest);

    List<Order> getOrdersForUser(int userId);

    boolean checkOrderStatusIsNotSuccessful(int orderId);
}
