package com.cs490.shoppingcart.administrationmodule.payload.request;


import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
  private String name;
  private String email;
  private String password;
  private Set<String> roles;
}
