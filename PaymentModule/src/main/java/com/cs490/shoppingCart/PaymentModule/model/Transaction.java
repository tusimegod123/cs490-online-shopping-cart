package com.cs490.shoppingCart.PaymentModule.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private Integer userId;
    private Integer orderId;
    private String cardNumber;
    private Double cardBalance;
    private Double transactionValue;
    private TransactionStatus transactionStatus;
    private String transactionNumber;
    private Date transactionDate;

}

