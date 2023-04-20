package com.cs490.shoppingCart.OrderProcessingModule.exception;

public class OrderAlreadyPaid extends RuntimeException{
    public OrderAlreadyPaid(String message){
        super(message);
    }

}
