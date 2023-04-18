package com.cs490.shoppingCart.NotificationModule.service;

import java.util.Date;

public class TransactionDTO {
  private Long userId;
  private Long orderId;
  private double transactionValue;
  private String transactionNumber;
  private Date transactionDate;
  private String paymentType;
  private String transactionType;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public double getTransactionValue() {
    return transactionValue;
  }

  public void setTransactionValue(double transactionValue) {
    this.transactionValue = transactionValue;
  }

  public String getTransactionNumber() {
    return transactionNumber;
  }

  public void setTransactionNumber(String transactionNumber) {
    this.transactionNumber = transactionNumber;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }
}
