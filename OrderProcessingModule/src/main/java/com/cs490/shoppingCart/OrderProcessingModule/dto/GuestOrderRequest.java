package com.cs490.shoppingCart.OrderProcessingModule.dto;

import com.cs490.shoppingCart.OrderProcessingModule.dto.PaymentInfoDTO;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.ShoppingCart;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GuestOrderRequest {

    private UserDTO userInfo;
    private PaymentInfoDTO paymentInfo;
    private ShoppingCart shoppingCart;
}
