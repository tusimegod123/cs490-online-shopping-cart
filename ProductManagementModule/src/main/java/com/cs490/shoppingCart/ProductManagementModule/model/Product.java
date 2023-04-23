package com.cs490.shoppingCart.ProductManagementModule.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Name is required")
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
