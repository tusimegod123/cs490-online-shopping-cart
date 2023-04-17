package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymentInfoDTO {

    private String cardNumber;
    private String nameOnCard;
    private String CCV;
    private LocalDate cardExpiry;
}
