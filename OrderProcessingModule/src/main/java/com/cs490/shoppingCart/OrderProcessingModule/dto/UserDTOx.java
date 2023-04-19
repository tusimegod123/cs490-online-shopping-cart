package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTOx {

    private Long id;
    private String name;
    private String email;
    private String telephoneNumber;


}
