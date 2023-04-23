package com.cs490.shoppingCart.ProfitSharingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitRequest {

    private Long transactionId;

    private Long orderId;

    private Double transactionValue;

    private String transactionNumber;

    private String transactionDate;
}
