package com.cs490.shoppingCart.NotificationModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String address_id;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
