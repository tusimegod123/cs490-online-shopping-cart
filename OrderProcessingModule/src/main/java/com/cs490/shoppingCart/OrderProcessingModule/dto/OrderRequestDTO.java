package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDTO {

    private ShoppingCartDTO shoppingCart;
    private PaymentInfoDTO paymentInfoDTO;


}
