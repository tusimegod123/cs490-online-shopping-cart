package com.cs490.shoppingCart.ProductManagementModule.model;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String telephoneNumber;
    private String username;
    private Boolean isVerified;
    private Boolean isFullyVerified;
    private String verifiedBy;
    private Set<Role> roles;
}
