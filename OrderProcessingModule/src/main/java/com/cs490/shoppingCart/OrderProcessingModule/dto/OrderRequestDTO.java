package com.cs490.shoppingCart.OrderProcessingModule.dto;

import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDTO {

    private ShoppingCart shoppingCart;
    private PaymentInfoDTO paymentInfoDTO;

}
