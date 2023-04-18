package com.cs490.shoppingCart.PaymentModule.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfitShareRequest {

    private Long Id;

    private Long orderId;

    private Double transactionValue;

    private String transactionNumber;

    @Temporal(TemporalType.DATE)
    private Date transactionDate;
}
