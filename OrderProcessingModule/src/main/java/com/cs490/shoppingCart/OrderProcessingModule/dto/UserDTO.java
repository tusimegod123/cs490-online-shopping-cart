package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

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
        public UserDTO(String name, String email,String telephoneNumber,Set<Role> roles){
                this.email = email;
                this.telephoneNumber = telephoneNumber;
                this.name =  name;
                this.roles = roles;
        }
}
