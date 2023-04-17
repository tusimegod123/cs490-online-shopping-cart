package com.cs490.shoppingCart.PaymentModule.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {
    private PaymentType paymentType;
    private Double currentBalance;
}