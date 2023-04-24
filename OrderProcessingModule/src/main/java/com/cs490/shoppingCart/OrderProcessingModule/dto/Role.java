package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer roleId;
    private String roleName;

    public Role(Integer roleId) {
        this.roleId = roleId;
    }

}
