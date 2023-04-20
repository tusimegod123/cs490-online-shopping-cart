package com.cs490.shoppingCart.OrderProcessingModule.controller;

import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderList;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderRequestDTO;
import com.cs490.shoppingCart.OrderProcessingModule.exception.OrderlineEmptyException;
import com.cs490.shoppingCart.OrderProcessingModule.model.*;
import com.cs490.shoppingCart.OrderProcessingModule.dto.GuestOrderRequest;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId){
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
    public ResponseEntity<?> getOrderForUser(@PathVariable Long userId){
        //check user existance from godwin
        List<Order> orderList = orderService.getOrdersForUser(userId);
        return new ResponseEntity<>(orderList,HttpStatus.OK);
        //}
    }
    @GetMapping("/{orderId}/checkExistance")
    public ResponseEntity<?> orderExist(@PathVariable Long orderId){
        boolean exist = orderService.checkOrderExistance(orderId);
        return new ResponseEntity<>(orderService.checkOrderExistance(orderId),HttpStatus.OK);

    }
    @GetMapping("/reports")
    public ResponseEntity<OrderList> getAllOrdersForSpecificVendor(@RequestParam(value="initalDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate initalDate,
                                                                   @RequestParam(value = "finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate finalDate,
                                                                   @RequestParam(value = "vendorId" ,required = false) Long vendorId) {

        OrderList orderList = new OrderList();
        orderList.setOrders(orderService.getAllOrdersForReport(initalDate,finalDate,vendorId));
        return new ResponseEntity<>(orderList,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createOrderRegisteredUser(@RequestBody OrderRequestDTO orderRequestDTO){

        if(!orderRequestDTO.getShoppingCart().getCartLines().isEmpty()){
            Order order = orderService.createOrder(orderRequestDTO);
            return new ResponseEntity<>(order,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new OrderlineEmptyException("You don't have any item to order"),HttpStatus.NOT_FOUND);
    }


    @PostMapping("/guestUser")
    public ResponseEntity<?> createOrderForGuestUser(@RequestBody GuestOrderRequest guestOrderRequest){
        if(!guestOrderRequest.getShoppingCart().getCartLines().isEmpty()){
            OrderDTO order = orderService.createGuestOrder(guestOrderRequest);
            return new ResponseEntity<>(order,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new OrderlineEmptyException("You don't have any item to order"),HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{orderId}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable Long orderId){
        if(orderService.checkOrderExistance(orderId)){
            if(orderService.checkOrderStatusIsNotSuccessful(orderId))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}









