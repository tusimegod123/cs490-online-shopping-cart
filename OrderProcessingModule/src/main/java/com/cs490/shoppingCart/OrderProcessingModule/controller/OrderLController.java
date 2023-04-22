package com.cs490.shoppingCart.OrderProcessingModule.controller;

import com.cs490.shoppingCart.OrderProcessingModule.dto.*;
import com.cs490.shoppingCart.OrderProcessingModule.exception.OrderlineEmptyException;
import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
import com.cs490.shoppingCart.OrderProcessingModule.model.Orders;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("api/v2/orders")
public class OrderLController {
    @Autowired
    private OrderService2 orderService;

    @PostMapping()
    public ResponseEntity<?> createOrderRegisteredUser(@RequestBody OrderRequestDT orderRequestDTO){
            Order order = orderService.createOrder(orderRequestDTO);
            return new ResponseEntity<>(order,HttpStatus.CREATED);

    }

}
