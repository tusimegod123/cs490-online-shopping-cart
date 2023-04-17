package com.cs490.shoppingCart.PaymentModule.model;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentType;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String transactionNumber;

    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

}

