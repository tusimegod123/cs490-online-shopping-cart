package com.cs490.shoppingCart.PaymentModule.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDetail {
    private String cardNumber;
    private String name;
    private String CCV ;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate cardExpiry;
}