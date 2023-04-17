package com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String telephoneNumber;


}
