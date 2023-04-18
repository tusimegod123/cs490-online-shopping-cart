package com.cs490.shoppingCart.OrderProcessingModule.exception;

public class OrderlineEmptyException extends RuntimeException{

    public OrderlineEmptyException(String message){
        super(message);
    }
}
