package com.cs490.shoppingCart.ProfitSharingModule.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {

    private Long Id;
    private Long userId;
    private Long orderId;
    private Double transactionValue;
    private String transactionNumber;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

}
