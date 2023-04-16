package com.cs490.shoppingCart.PaymentModule.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String emailTo;
    private String vPassword;
    private Integer userId;
    private Integer orderId;
    private Integer fromSystemType ;
    private Boolean approved;
}