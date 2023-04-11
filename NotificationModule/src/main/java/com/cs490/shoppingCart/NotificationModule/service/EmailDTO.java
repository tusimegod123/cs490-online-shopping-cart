package com.cs490.shoppingCart.NotificationModule.service;

public class EmailDTO {
    private String emailTo;
    private String emailSubject;
    private String emailContent;

    public EmailDTO(String emailTo, String emailSubject, String emailContent) {
        this.emailTo = emailTo;
        this.emailSubject = emailSubject;
        this.emailContent = emailContent;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }
}
