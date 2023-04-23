package com.cs490.shoppingCart.OrderProcessingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
        private List<Role> roles;
        public UserDTO(String name, String email,String telephoneNumber,List<Role> roles){
                this.email = email;
                this.telephoneNumber = telephoneNumber;
                this.name =  name;
                this.roles = roles;
        }
}
