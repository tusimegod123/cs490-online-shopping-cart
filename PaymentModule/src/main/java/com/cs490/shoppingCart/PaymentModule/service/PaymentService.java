package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequest;
import com.cs490.shoppingCart.PaymentModule.DTO.RegistrationPayment;
import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;

import java.util.List;

public interface PaymentService {
    TransactionStatus processOrderPayment(PaymentRequest request) throws Exception;

    TransactionStatus processRegistrationPayment(RegistrationPayment request) throws Exception;

    List<Transaction> getAll();

    List<Transaction> findByUserId(Integer id);

    Transaction findByOrderId(Integer id);
}
