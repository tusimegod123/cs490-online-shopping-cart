package com.cs490.shoppingCart.ReportingModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String user_id;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String role_id;
    private AddressDTO userAddress;
    private double initial_pay;
    private String acoount_status;
}
