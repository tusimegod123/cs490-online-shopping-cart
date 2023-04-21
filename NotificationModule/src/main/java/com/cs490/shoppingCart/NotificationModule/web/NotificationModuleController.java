package com.cs490.shoppingCart.NotificationModule.web;

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
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO email) throws Exception{

        senderService.formatAndSendEmail(null,email);
        return new ResponseEntity<>("An email has been sent successfully!", HttpStatus.OK);
    }

    @PostMapping(path = "/email/transaction")
    @ResponseBody
    public ResponseEntity<String> sendTransactionEmail(@RequestBody TransactionDTO transac) throws Exception{

        senderService.formatAndSendEmail(transac, null);
        return new ResponseEntity<>("An email has been sent successfully!", HttpStatus.OK);
    }

}