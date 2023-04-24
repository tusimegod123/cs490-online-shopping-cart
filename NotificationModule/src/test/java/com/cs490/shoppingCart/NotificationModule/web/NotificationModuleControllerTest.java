package com.cs490.shoppingCart.NotificationModule.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cs490.shoppingCart.NotificationModule.service.EmailDTO;
import com.cs490.shoppingCart.NotificationModule.service.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class NotificationModuleControllerTest {
    /**
     * Method under test: {@link NotificationModuleController#sendEmail(EmailDTO)}
     */
    @Test
    void testSendEmail() {

        // Arrange
        NotificationModuleController notificationModuleController = new NotificationModuleController();

        // Act
        ResponseEntity<?> actualSendEmailResult = notificationModuleController.sendEmail(new EmailDTO());

        // Assert
        assertTrue(actualSendEmailResult.hasBody());
        assertEquals(400, actualSendEmailResult.getStatusCodeValue());
        assertTrue(actualSendEmailResult.getHeaders().isEmpty());
    }


    /**
     * Method under test: {@link NotificationModuleController#sendTransactionEmail(TransactionDTO)}
     */
    @Test
    void testSendTransactionEmail() throws Exception {

        // Arrange
        NotificationModuleController notificationModuleController = new NotificationModuleController();

        // Act
        ResponseEntity<?> actualSendTransactionEmailResult = notificationModuleController
                .sendTransactionEmail(new TransactionDTO());

        // Assert
        assertTrue(actualSendTransactionEmailResult.hasBody());
        assertEquals(400, actualSendTransactionEmailResult.getStatusCodeValue());
        assertTrue(actualSendTransactionEmailResult.getHeaders().isEmpty());
    }

}

