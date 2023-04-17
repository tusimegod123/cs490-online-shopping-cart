package com.cs490.shoppingCart.PaymentModule.controller;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequest;
import com.cs490.shoppingCart.PaymentModule.DTO.RegistrationPayment;
import com.cs490.shoppingCart.PaymentModule.model.Transaction;
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

    @PostMapping("/payOrder")
    public ResponseEntity<TransactionStatus> makePayment(@RequestBody PaymentRequest request) throws Exception {
        TransactionStatus  status = paymentService.processOrderPayment(request);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/payRegistrationFee")
    public ResponseEntity<TransactionStatus> registrationFee(@RequestBody RegistrationPayment request) throws Exception {
        TransactionStatus  status = paymentService.processRegistrationPayment(request);
        return ResponseEntity.ok().body(status);
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<?> getAll(){
        List<Transaction> transactions = paymentService.getAll();
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/getTransactionsByUserId/{id}")
    public ResponseEntity<?> getByUserID(@PathVariable Integer id){
        List<Transaction> transactions = paymentService.findByUserId(id);
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/getTransactionByOrderId/{id}")
    public ResponseEntity<?> getByOrderID(@PathVariable Integer id){
        Transaction transaction = paymentService.findByOrderId(id);
        return ResponseEntity.ok().body(transaction);
    }
}