package com.cs490.shoppingCart.OrderProcessingModule.service;


import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderRequestDTO;
import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
import com.cs490.shoppingCart.OrderProcessingModule.dto.GuestOrderRequest;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    Order createOrder(OrderRequestDTO orderRequestDTO);



    boolean checkOrderExistance(Long id);


    Order getOrder(Long orderId);

    OrderDTO createGuestOrder(GuestOrderRequest guestOrderRequest);

    List<Order> getOrdersForUser(Long userId);

    boolean checkOrderStatusIsNotSuccessful(Long orderId);

     List<OrderDTO> getAllOrdersForReport(LocalDate initalDate, LocalDate finalDate, Long vendorId);
}