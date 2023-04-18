package com.cs490.shoppingCart.ProductManagementModule.model;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telephoneNumber;
    private String username;
}
