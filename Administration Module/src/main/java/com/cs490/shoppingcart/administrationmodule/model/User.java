package com.cs490.shoppingcart.administrationmodule.model;

import com.cs490.shoppingcart.administrationmodule.dto.RoleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Data
@AllArgsConstructor
@Entity
//@Table(name = "all_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telephoneNumber;
    private String username;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonSerialize
    private Set<Role> roles;
//@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//@Enumerated(EnumType.STRING)
//private List<Role> roles = new ArrayList<>();

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
        this.roles = new HashSet<>();
    }
    public User(Set<Role> roles) {
        this();
        setRoles(roles);
    }

//    public User(User user) {
//        this.password = user.getPassword();
//    }

//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        String[] userRoles = (String[])this.getRoles().stream().map((role) -> {
//            return role.getRoleName();
//        }).toArray((x$0) -> {
//            return new String[x$0];
//        });
//        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
//        return authorities;
//    }
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    String[] userRoles = getRoles().stream().map((role) -> role.getRoleName()).toArray(String[]::new);
    Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
    return authorities;
}
    @Override
    public String getUsername() {
        return this.email;
    }
}

