package com.cs490.shoppingCart.OrderProcessingModule.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandle {


        @ExceptionHandler(value = { Exception.class })
        public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
            String errorMessage = ex.getMessage();
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

//e

            return new ResponseEntity<>(errorMessage, status);
        }
    }

