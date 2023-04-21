package com.cs490.shoppingCart.NotificationModule.service;

import com.cs490.shoppingCart.NotificationModule.integration.ShoppingCartApplicationRestClient;
import com.cs490.shoppingCart.NotificationModule.util.AppInfo;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailSenderService.class);
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AppInfo appInfo;

    private static ShoppingCartApplicationRestClient restClient = new ShoppingCartApplicationRestClient();

    public void sendSimpleEmail(MimeMessage mime) {
        mailSender.send(mime);
        LOG.info("##############An email has been sent successfully###############");

    }

    public void formatAndSendEmail(TransactionDTO transaction, EmailDTO email)
            throws MessagingException, UnsupportedEncodingException {

        System.out.println(appInfo.getUserUrl());
        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mime);
        message.setFrom(appInfo.getFromMail(), "CS490MP-T3 Online Store");
        StringBuilder body = new StringBuilder("<html><body><div><br><br>");
        Long userId = transaction != null ? transaction.getUserId() : email.getUserId();

        UserDTO user = restClient.getUser(userId);
        AddressDTO address = user != null ? user.getUserAddress() : null;

        if (transaction != null) {
            // Order confirmation email
            message.setTo(user.getEmail());
            if (transaction.getTransactionType().equalsIgnoreCase("OrderPayment")) {
                message.setSubject("Order Confirmation: Thank you for your purchase!");
                body.append("<H2 style='text-align:center;'>Dear " + user.getName() + "</H2>");
                body.append(
                        "<H3 style='text-align:center;'>Thank you for your order! We hope you enjoyed shopping with us.</H3>");
                body.append(
                        "<br><div style='text-align:center;'><button style='width:40%; background:black; color:white;' type='button' onclick='#'>Order Information</button></div>");

                body.append("<table style='margin-left: auto;margin-right: auto;'>");
                body.append(
                        "<tr><td><p style='font-size:18px;'><b>Order Number:</b></p></td><td>      </td><td><p style='font-size:18px;'><b>Total Amount:</b></p></td></tr>");
                body.append("<tr><td>" + transaction.getOrderId() + "</td><td>   </td><td>"
                        + transaction.getTransactionValue() + "</td></tr>");

                body.append(
                        "<tr><td><br><p style='font-size:18px;'><b>Shipping method:</b></p></td><td>      </td><td><br><p style='font-size:18px;'><b>Payment method:</b></p></td></tr>");
                body.append("<tr><td>Standard shipping</td><td>      </td><td>Mastercard</td></tr>");

                body.append(
                        "<tr><td><br><p style='font-size:18px;'><b>Shipping address:</b></p></td><td>      </td><td><br><p style='font-size:18px;'><b>Payment date:</b></p></td></tr>");
                body.append("<tr><td>" + address.getStreet() + "<br>" + address.getCity() + ", " + address.getState() +
                        "<br>" + address.getCountry() + "<br>" + address.getZipcode() + "</td><td>      </td><td>"
                        + transaction.getTransactionDate() + "</td></tr>");

                body.append(
                        "<tr><td><br><p style='font-size:18px;'><b>Estimated shipping time:</b></p></td><td>      </td><td> </td></tr>");
                body.append("<tr><td>3 days</td><td>      </td><td> </td></tr></table><br><br>");
            } else {
                // RegistrationFee
                message.setSubject("Payment Confirmation: We've received your payment!");
                body.append("<H2 style='text-align:center;'>Dear " + user.getName() + "</H2>");
                body.append("<H3 style='text-align:center;'>Thanks for your payment</H3>");

                body.append(
                        "<br><br><p style='text-align:center;'>Your payment of $" + transaction.getTransactionValue()
                                + " posted to us on " + transaction.getTransactionDate() + "<br><br><br>");
            }

        }

        if (email != null) {
            message.setTo(user.getEmail());
            body.append("<H2 style='text-align:center;'>Dear " + user.getName() + "</H2>");
            if (email.getEmailType().equalsIgnoreCase("WelcomeEmail")) {
                message.setSubject("Welcome to our online store!");
                body.append("<H3 style='text-align:center;'>Thank you for doing business with us.<br><br></H3>");
                body.append(
                        "<H4 style='text-align:center;'>Please take note of your login details below:<br>Username:" +
                                user.getUsername() + "<br>Password:" + email.getPassword() + "</H4><br><br>");
            } else {
                // OtherCommunication - products un/approved
                String msg = email.getMessage() != null && !email.getMessage().isEmpty() ? email.getMessage()
                        : "Congratulations! Your recent products has been approved";
                message.setSubject("An update on your recent products with us");
                body.append("<H4 style='text-align:center;'>" + msg + ".</H4><br><br>");
            }

        }

        body.append(
                "<h3 style='text-align:center;'>This is an automatically generated email, please do not reply.</h3>");
        body.append("</div></body></html>");
        message.setText(body.toString(), true);

        sendSimpleEmail(mime);
    }

}
