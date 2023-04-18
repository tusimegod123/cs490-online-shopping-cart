package com.cs490.shoppingCart.NotificationModule.service;


public class UserDTO {
    private Long user_id;
    private String username;
    private String fullname;
    private String password;
    private String email;
    private String role_id;
    private AddressDTO userAddress;
    private double initial_pay;
    private String acoount_status;

    public UserDTO(Long user_id, String username, String fullname, String password, String email, String role_id, AddressDTO userAddress, double initial_pay, String acoount_status) {
        this.user_id = user_id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.role_id = role_id;
        this.userAddress = userAddress;
        this.initial_pay = initial_pay;
        this.acoount_status = acoount_status;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public AddressDTO getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(AddressDTO userAddress) {
        this.userAddress = userAddress;
    }

    public double getInitial_pay() {
        return initial_pay;
    }

    public void setInitial_pay(double initial_pay) {
        this.initial_pay = initial_pay;
    }

    public String getAcoount_status() {
        return acoount_status;
    }

    public void setAcoount_status(String acoount_status) {
        this.acoount_status = acoount_status;
    }
}
