package com.cs490.shoppingCart.NotificationModule.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String name;
    private String password;
    private String email;
    private String roleId;
    private AddressDTO userAddress;
    private double initial_pay;
    private String acoount_status;
}
