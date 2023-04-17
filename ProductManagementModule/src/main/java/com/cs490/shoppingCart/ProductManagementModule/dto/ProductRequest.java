package com.cs490.shoppingCart.ProductManagementModule.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private String description;
    private Double price;
    private Double itemCost;
    private Integer quantity;
    private String imageUrl;
    private Long vendorId;
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
}
