package com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GuestOrderRequest {

    private String Address;
    private String name;
    private String email;
    private String telephone;
    private String paymentCardNumber;
    private ShoppingCart shoppingCart;
}
