package com.cs490.shoppingCart.OrderProcessingModule.controller;

import com.cs490.shoppingCart.OrderProcessingModule.exception.OrderlineEmptyException;
import com.cs490.shoppingCart.OrderProcessingModule.model.*;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.CartLine;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.GuestOrderRequest;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.ShoppingCart;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderId){

        if(orderService.checkOrderExistance(orderId)){
            Order order = orderService.getOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<Orders> getAllOrders(){
        Orders orders =  new Orders(orderService.getOrders());
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
    @GetMapping("users/{userId}")
    public ResponseEntity<?> getOrderForUser(@PathVariable int userId){
        //check user existance from godwin
            List<Order> orderList = orderService.getOrdersForUser(userId);
            return new ResponseEntity<>(orderList,HttpStatus.OK);
        //}
    }
    @GetMapping("/{orderId}/checkExistance")
    public ResponseEntity<?> orderExist(@PathVariable int orderId){
        return new ResponseEntity<>(orderService.checkOrderExistance(orderId),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createOrderRegisteredUser(@RequestBody ShoppingCart shoppingCart){
        if(!shoppingCart.getCartLines().isEmpty()){
            //shoppingCart.getCartLines()
        Order order = orderService.createOrder(shoppingCart);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new OrderlineEmptyException("You don't have any item to order"),HttpStatus.NOT_FOUND);
    }
    @PostMapping("/guestUser")
    public ResponseEntity<?> createOrderForGuestUser(@RequestBody GuestOrderRequest guestOrderRequest){
        if(guestOrderRequest.getShoppingCart().getCartLines().isEmpty()){

            Order order = orderService.createGuestOrder(guestOrderRequest);
        }
        return new ResponseEntity<>(new OrderlineEmptyException("You don't have any item to order"),HttpStatus.NOT_FOUND);
    }









}
