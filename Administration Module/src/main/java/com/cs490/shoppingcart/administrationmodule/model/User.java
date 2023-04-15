package com.cs490.shoppingcart.administrationmodule.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Data
@AllArgsConstructor
@Entity
//@Table(name = "all_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String telephoneNumber;
    private String username;
    private Boolean isVerified;
    private Boolean isFullyVerified;
    private String paymentCardNumber;
    private String verifiedBy;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonSerialize
    private Set<Role> roles;

    // Data fields needed for implementing methods of UserDetails interface
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public User() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.isFullyVerified=false;
        this.roles = new HashSet<>();
    }

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<Role> roles = getRoles();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    for (Role role : roles) {
        authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
    }
    return authorities;
}

        public void setUsername(String username) {
            this.username = username;
        }
    @Override
    public String getUsername() {
        return this.username;
    }
    public void addRole(Role role) {
        this.roles.add(role);
    }
}
