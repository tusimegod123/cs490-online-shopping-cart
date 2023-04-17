package com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Address {

    private String street;
    private String city;
    private String state;
    private Integer zipcode;
    private String country;

}
