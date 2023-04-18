package com.cs490.shoppingCart.PaymentModule.DTO;

import com.cs490.shoppingCart.PaymentModule.model.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

    private Long userId;
    private Long orderId;
    private Double transactionValue;
    private String transactionNumber;

    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}