package com.cs490.shoppingCart.PaymentModule.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailDTO {
    private String cardNumber;
    private String name;
    private String CCV ;
    private Date cardExpiry;
}