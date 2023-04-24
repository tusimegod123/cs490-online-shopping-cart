package com.cs490.shoppingCart.ProductManagementModule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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


    @Column(columnDefinition="TEXT")
    private String description;

    @NotBlank(message = "Image URL is required")

    @Column(columnDefinition="TEXT")
    private String imageUrl;

    private Boolean verified;

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotNull(message = "Category Id is required")
    private Long categoryId;



}
