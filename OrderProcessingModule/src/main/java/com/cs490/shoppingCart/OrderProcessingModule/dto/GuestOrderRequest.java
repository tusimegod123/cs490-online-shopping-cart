package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GuestOrderRequest {

    private UserDTO userInfo;
    private PaymentInfoDTO paymentInfo;
    private ShoppingCartDTO shoppingCart;
}
