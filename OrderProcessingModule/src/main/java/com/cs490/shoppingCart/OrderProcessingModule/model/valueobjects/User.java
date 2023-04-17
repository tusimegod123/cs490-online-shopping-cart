package com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {

    private Integer id;
    private String name;
    private String password;
    private String username;
    private String email;
    private String telephoneNumber;
    private boolean verified;
    private Role roles;

}
