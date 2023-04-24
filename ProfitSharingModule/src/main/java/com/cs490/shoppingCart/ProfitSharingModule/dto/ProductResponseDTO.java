package com.cs490.shoppingCart.ProfitSharingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseDTO {
    private Long productId;
    private Double price;
    private Double itemCost;

}
