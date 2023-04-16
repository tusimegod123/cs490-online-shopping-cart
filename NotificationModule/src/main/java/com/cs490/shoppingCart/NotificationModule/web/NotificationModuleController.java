package com.cs490.shoppingCart.NotificationModule.web;

import com.cs490.shoppingCart.NotificationModule.service.EmailDTO;
import com.cs490.shoppingCart.NotificationModule.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The Class NotificationController.
 */
@RestController
@RequestMapping("/notification")
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

        senderService.sendSimpleEmail(email);
        return new ResponseEntity<>("An email has been sent successfully!", HttpStatus.OK);
    }


}