package com.cs490.shoppingcart.administrationmodule.dto;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telephoneNumber;
    private String username;
    private Set<Role> roles;
}
