package com.cs490.shoppingCart.OrderProcessingModule.service;

import com.cs490.shoppingCart.OrderProcessingModule.dto.GuestOrderRequest;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderRequestDT;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderRequestDTO;
import com.cs490.shoppingCart.OrderProcessingModule.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService2 {


    Order createOrder(OrderRequestDT orderRequestDTO);



}
