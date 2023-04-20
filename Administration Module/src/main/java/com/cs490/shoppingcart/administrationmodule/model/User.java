package com.cs490.shoppingcart.administrationmodule.model;

import com.cs490.shoppingcart.administrationmodule.dto.EmailType;
import com.cs490.shoppingcart.administrationmodule.dto.NotificationRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    @OneToMany
//    @JsonSerialize

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId")}
    )
    private List<Role> roles;

    private String message;
    private String emailType;

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
        this.roles = new ArrayList<>();
    }

    public User(String name, String email, String password, String telephoneNumber, String username, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephoneNumber = telephoneNumber;
        this.username = username;
        this.roles = roles;
    }

    @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    List<Role> roles = getRoles();
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

    @JsonIgnore
    public NotificationRequest getNotificationRequest(){
        return new NotificationRequest(this.userId, this.message,this. emailType= "WelcomeEmail", this.password);
    }
}
