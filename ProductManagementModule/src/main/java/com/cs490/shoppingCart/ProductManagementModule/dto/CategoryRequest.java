package com.cs490.shoppingCart.ProductManagementModule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    private Long id;

    @NotBlank(message = "name is required")
    private String name;
    private String description;
}
