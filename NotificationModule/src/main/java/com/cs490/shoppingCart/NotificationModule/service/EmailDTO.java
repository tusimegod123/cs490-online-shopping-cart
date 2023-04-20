package com.cs490.shoppingCart.NotificationModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private Long userId;
    private String emailType;
    private String message;
}
