package com.cs490.shoppingCart.ProductManagementModule.dto;

import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Data
public class CreateProductResponse {

    private Long productId;
    private String productName;
    private Double price;
    private int amount;
    private String description;
    private String imageUrl;
    private Boolean verified;

    private SecurityProperties.User user;
//    private Category category;
}
