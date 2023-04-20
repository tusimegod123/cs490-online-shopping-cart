package com.cs490.shoppingCart.NotificationModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
  private Long userId;
  private Long orderId;
  private double transactionValue;
  private String transactionNumber;
  private LocalDate transactionDate;
  private String paymentType;
  private String transactionType;
}
