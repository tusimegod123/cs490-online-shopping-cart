package com.cs490.shoppingCart.ProfitSharingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDTO {
    private Long id;
    private Integer quantity;
    private Double price;
    private String productInfo;
}
