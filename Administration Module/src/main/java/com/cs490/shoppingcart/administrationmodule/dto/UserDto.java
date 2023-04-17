package com.cs490.shoppingcart.administrationmodule.dto;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
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

    public UserDto(String name, String email, String telephoneNumber, String username, Boolean isVerified, Boolean isFullyVerified, String verifiedBy, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.username = username;
        this.isVerified = isVerified;
        this.isFullyVerified = isFullyVerified;
        this.verifiedBy = verifiedBy;
        this.roles = roles;
    }


}
