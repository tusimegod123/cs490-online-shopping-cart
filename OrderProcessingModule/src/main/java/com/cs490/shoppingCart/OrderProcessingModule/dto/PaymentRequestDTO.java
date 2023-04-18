package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PaymentRequestDTO {

    private Integer userId;
    private Integer orderId;
    private Double amount;
    private PaymentInfoDTO paymentInfoDTO;


}
