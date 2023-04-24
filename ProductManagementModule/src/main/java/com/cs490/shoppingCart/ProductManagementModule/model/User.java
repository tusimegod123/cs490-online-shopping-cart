package com.cs490.shoppingCart.ProductManagementModule.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class User {

    private Long userId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone Number is required")
    private String telephoneNumber;

    @NotBlank(message = "Username is required")
    private String username;
    private Boolean isVerified;
    private Boolean isFullyVerified;
    private String verifiedBy;

    @NotEmpty(message = "Role is required")
    private Set<Role> roles;
}
