package com.cs490.shoppingCart.NotificationModule.service;

import com.cs490.shoppingCart.NotificationModule.integration.ShoppingCartApplicationRestClient;
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
    @Value("${spring.mail.username}")
    private static String fromMail;
    private static ShoppingCartApplicationRestClient restClient= new ShoppingCartApplicationRestClient();

    public void sendSimpleEmail(EmailDTO email
    ) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message= formatEmailBody(email);
        mailSender.send(message);
        LOG.info("##############An email has been sent successfully###############");


    }

    public MimeMessage formatEmailBody(EmailDTO email) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mime =mailSender.createMimeMessage();
        MimeMessageHelper message=new MimeMessageHelper(mime);
        message.setFrom("cs490@gmail.com", "CS490MP-T3 Online Store");
        StringBuilder body=new StringBuilder("<html><body><div><br><br>");

        OrderDTO order = restClient.getOrder(email.getOrderId());
        UserDTO user = restClient.getUser(email.getUserId());
        AddressDTO address= user!=null? user.getUserAddress():null;

        if(email.getFromSystemType() == 0){
            //Order confirmation email
            message.setTo(email.getEmailTo());
            message.setSubject("Order Confirmation: Thank you for your purchase!");
            body.append("<H2 style='text-align:center;'>Dear "+ user.getFullname()+ "</H2>");
            body.append("<H3 style='text-align:center;'>Thank you for your order! We hope you enjoyed shopping with us.</H3>");
            body.append("<br><div style='text-align:center;'><button style='width:40%; background:black; color:white;' type='button' onclick='#'>Order Information</button></div>");

            body.append("<table style='margin-left: auto;margin-right: auto;'>");
            body.append("<tr><td><p style='font-size:18px;'><b>Order Number:</b></p></td><td>      </td><td><p style='font-size:18px;'><b>Total Amount:</b></p></td></tr>");
            body.append("<tr><td>"+order.getOrder_id()+"</td><td>   </td><td>"+order.getTotal_price()+"</td></tr>");

            body.append("<tr><td><br><p style='font-size:18px;'><b>Shipping method:</b></p></td><td>      </td><td><br><p style='font-size:18px;'><b>Payment method:</b></p></td></tr>");
            body.append("<tr><td>Standard shipping</td><td>      </td><td>Mastercard</td></tr>");

            body.append("<tr><td><br><p style='font-size:18px;'><b>Shipping address:</b></p></td><td>      </td><td><br><p style='font-size:18px;'><b>Payment date:</b></p></td></tr>");
            body.append("<tr><td>"+address.getStreet()+"<br>"+address.getCity()+", "+ address.getState()+
                    "<br>"+address.getCountry()+"<br>"+address.getZipcode()+"</td><td>      </td><td>"+order.getOrder_date()+"</td></tr>");

            body.append("<tr><td><br><p style='font-size:18px;'><b>Estimated shipping time:</b></p></td><td>      </td><td> </td></tr>");
            body.append("<tr><td>3 days</td><td>      </td><td> </td></tr></table><br><br>");

            body.append("<h3 style='text-align:center;'>This is an automatically generated email, please do not reply.</h3>");

        }
        if(email.getFromSystemType() == 1){
            message.setTo(email.getEmailTo());
            message.setSubject("Welcome to our online store!");
            body.append("<H2 style='text-align:center;'>Dear "+ user.getFullname()+ "</H2>");
            body.append("<H3 style='text-align:center;'>Thank you for doing business with us.<br><br></H3>");
            body.append("<H4 style='text-align:center;'>Please take note of your login password below:<br>Password:"+
                    email.getvPassword()+"</H4><br><br>");

            body.append("<h3 style='text-align:center;'>This is an automatically generated email, please do not reply.</h3>");
        }
        if(email.getFromSystemType() == 2){
            message.setTo(email.getEmailTo());
            message.setSubject("Update about new products");

        }

        body.append("</div></body></html>");
        message.setText(body.toString(), true);
        return mime;
    }

}
