package com.cs490.shoppingCart.ProductManagementModule.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private Double price;
    private Integer qty;
    private Double itemCost;
    private String description;
    private String imageUrl;
    private Boolean verified;
    private Long userId;
    private Long categoryId;
}
