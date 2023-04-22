package com.cs490.shoppingCart.NotificationModule.web;

import com.cs490.shoppingCart.NotificationModule.exception.EmailNotSentException;
import com.cs490.shoppingCart.NotificationModule.service.EmailDTO;
import com.cs490.shoppingCart.NotificationModule.service.EmailSenderService;
import com.cs490.shoppingCart.NotificationModule.service.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The Class NotificationController.
 */
@RestController
public class NotificationModuleController {

    @Autowired
    private EmailSenderService senderService;

    /**
     * Send email endpoint.
     *
     * @param email the email object
     * @return the String
     */
    @PostMapping(path = "/email")
    @ResponseBody
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO email){
        if(email ==null){
            return new ResponseEntity<>(new EmailNotSentException("Invalid input"), HttpStatus.BAD_REQUEST);
        }
        try{
            senderService.formatAndSendEmail(null,email);
        }catch(Exception e){
            return new ResponseEntity<>(new EmailNotSentException(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("An email has been sent successfully!", HttpStatus.OK);
    }

    @PostMapping(path = "/email/transaction")
    @ResponseBody
    public ResponseEntity<?> sendTransactionEmail(@RequestBody TransactionDTO transac) throws Exception{
        if(transac ==null){
            return new ResponseEntity<>(new EmailNotSentException("Invalid input"), HttpStatus.BAD_REQUEST);
        }
        try{
            senderService.formatAndSendEmail(transac, null);
        }catch(Exception e){
            return new ResponseEntity<>(new EmailNotSentException(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("An email has been sent successfully!", HttpStatus.OK);
    }

}