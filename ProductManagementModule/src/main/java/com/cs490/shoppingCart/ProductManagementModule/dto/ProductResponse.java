package com.cs490.shoppingCart.ProductManagementModule.dto;

import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.model.User;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Data
public class ProductResponse {

    private Long productId;
    private String productName;
    private Double price;
    private Integer qty;
    private Double itemCost;
    private String description;
    private String imageUrl;
    private Boolean verified;

    private Long userId;
    private Category category;
}
