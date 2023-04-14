package com.cs490.shoppingCart.ProductManagementModule.dto;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String productName;
    private Double price;
    private int amount;
    private String description;
    private String imageUrl;
    private Boolean verified;

    private Long userId;
    private Long categoryId;
}
