package com.cs490.shoppingCart.NotificationModule.exception;

public class EmailNotSentException extends Exception{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new email not sent exception.
     *
     * @param message the message
     */
    public EmailNotSentException(String message) {
        super(message);
    }

}
