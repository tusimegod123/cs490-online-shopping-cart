package com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Category {

    private String name;
    private String description;
}

