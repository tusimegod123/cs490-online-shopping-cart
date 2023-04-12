package com.cs490.shoppingCart.PaymentModule.controller;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequestDTO;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;
import com.cs490.shoppingCart.PaymentModule.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body("Hello from payment module");
    }


    @PostMapping("/pay")
    public TransactionStatus makePayment(@RequestBody PaymentRequestDTO request) throws Exception {
        return paymentService.processPayment(request);
    }

}