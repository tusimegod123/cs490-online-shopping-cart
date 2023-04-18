package com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    private Integer userId;
    private Boolean cartStatus;
    private LocalDateTime cartDate;
    private Double totalPrice;
    private Set<CartLine> cartLines;
}
