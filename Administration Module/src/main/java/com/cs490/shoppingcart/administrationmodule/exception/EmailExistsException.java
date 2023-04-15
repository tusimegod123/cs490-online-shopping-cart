package com.cs490.shoppingcart.administrationmodule.exception;

public class EmailExistsException extends Exception{
    public EmailExistsException(String message){
        super(message);
    }
}
