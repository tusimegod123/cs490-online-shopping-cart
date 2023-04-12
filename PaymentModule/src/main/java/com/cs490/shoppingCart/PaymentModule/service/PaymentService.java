package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequestDTO;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;

public interface PaymentService {
    TransactionStatus processPayment(PaymentRequestDTO request) throws Exception;
}
