package com.cs490.shoppingCart.NotificationModule.service;

public class EmailDTO {
    private String emailTo;
    private String vPassword;
    private String userId;
    private String orderId;
    private int fromSystemType;

    private boolean approved;

    public EmailDTO(String emailTo, String vPassword, String userId, String orderId, int fromSystemType, boolean approved) {
        this.emailTo = emailTo;
        this.vPassword = vPassword;
        this.userId = userId;
        this.orderId = orderId;
        this.fromSystemType = fromSystemType;
        this.approved = approved;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getvPassword() {
        return vPassword;
    }

    public void setvPassword(String vPassword) {
        this.vPassword = vPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getFromSystemType() {
        return fromSystemType;
    }

    public void setFromSystemType(int fromSystemType) {
        this.fromSystemType = fromSystemType;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "emailTo='" + emailTo + '\'' +
                ", vPassword='" + vPassword + '\'' +
                ", userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", fromSystemType='" + fromSystemType + '\'' +
                '}';
    }
}
