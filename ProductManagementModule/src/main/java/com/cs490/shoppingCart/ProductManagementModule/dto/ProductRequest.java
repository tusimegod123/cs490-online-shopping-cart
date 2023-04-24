package com.cs490.shoppingCart.ProductManagementModule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Product Name is required")
    private String productName;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Quantity is required")
    private Integer qty;

    @NotNull(message = "Item Cost is required")
    private Double itemCost;

    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private Boolean verified;

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotNull(message = "Category Id is required")
    private Long categoryId;
}
