package com.cs490.shoppingCart.OrderProcessingModule.dto;

import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderDTO {

    private Long id;
    private Long userId;
    private Double totalPrice;
    private Set<OrderLine> orderLines;
    private String userInfo;
}
