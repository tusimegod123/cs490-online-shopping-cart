package com.cs490.shoppingCart.ProfitSharingModule.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ProfitRequest {

    private Long transactionId;

    private Long orderId;

    private Double transactionValue;

    private String transactionNumber;

    private Date transactionDate;
}
