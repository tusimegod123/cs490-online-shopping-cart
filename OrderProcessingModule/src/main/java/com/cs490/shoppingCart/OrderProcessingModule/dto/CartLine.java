package com.cs490.shoppingCart.OrderProcessingModule.dto;

import com.cs490.shoppingCart.OrderProcessingModule.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartLine {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price;
    private String productInfo;

}
