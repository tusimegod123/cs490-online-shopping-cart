package com.cs490.shoppingCart.ProfitSharingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private Long userId; //conform with hiwi
    private String name;
    private String description;
    private Double price;
    private String imgUrl;

}
