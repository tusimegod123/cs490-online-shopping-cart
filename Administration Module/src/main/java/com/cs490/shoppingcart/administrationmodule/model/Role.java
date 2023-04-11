package com.cs490.shoppingcart.administrationmodule.model;

import jakarta.persistence.*;
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
    private String roleName;
}
