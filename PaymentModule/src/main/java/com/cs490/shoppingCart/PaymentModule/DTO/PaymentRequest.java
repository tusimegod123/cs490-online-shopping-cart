package com.cs490.shoppingCart.PaymentModule.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long orderId;

    private Long userId;

    private Double amount;
    private String cardNumber;
    private String nameOnCard;
    private String CCV ;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    private LocalDate cardExpiry;
    private String cardExpiry;
    @JsonIgnore
    public CardDetail getCardDetail(){

        return new CardDetail(this.cardNumber,
                this.nameOnCard,
                this.CCV,
                this.cardExpiry);
    }
}