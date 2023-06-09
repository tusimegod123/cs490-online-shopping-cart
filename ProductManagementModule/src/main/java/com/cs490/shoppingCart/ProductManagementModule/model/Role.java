package com.cs490.shoppingCart.ProductManagementModule.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roleId;

    @NotBlank(message = "Role Name is required")
    private String roleName;

    public Role(String roleName) {

        this.roleName = roleName;
    }

    @Override
    public String toString() {

        return this.roleName;
    }
}
