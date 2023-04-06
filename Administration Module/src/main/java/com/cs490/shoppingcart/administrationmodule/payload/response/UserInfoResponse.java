package com.cs490.shoppingcart.administrationmodule.payload.response;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoResponse {
  private Long id;
  private String name;
  private String email;
  String telephoneNumber;
  private List<Role> role;

  public UserInfoResponse(Long id, String name,  String email, String telephoneNumber, List<Role> role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.telephoneNumber = telephoneNumber;
    this.role = role;
  }


}
