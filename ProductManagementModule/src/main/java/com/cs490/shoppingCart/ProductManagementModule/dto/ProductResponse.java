package com.cs490.shoppingCart.ProductManagementModule.dto;

import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Data
public class ProductResponse {

    private String productName;
    private String description;
    private Double price;
    private Double itemCost;
    private Integer quantity;
    private String imageUrl;
    private Long vendorId;
    private Boolean verified;
    private Category category;
}
