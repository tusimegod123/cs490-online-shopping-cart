package com.cs490.shoppingCart.ProductManagementModule.dto;

import lombok.Data;

@Data
public class ListProductResponseSpecificID {

    private Long productId;
    private Double price;
    private Double itemCost;

}
