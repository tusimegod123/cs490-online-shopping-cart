package com.cs490.shoppingCart.ProductManagementModule.dto;

import lombok.Data;

@Data
public class CategoryResponse {

    private Long categoryId;
    private String name;
    private String description;
}
