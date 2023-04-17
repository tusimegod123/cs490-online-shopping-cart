package com.cs490.shoppingCart.PaymentModule.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Integer orderId;

    private Integer userId;

    private Double amount;

    private String cardNumber;
    private String nameOnCard;
    private String CCV ;

//    @Temporal(TemporalType.DATE)
//    private Date cardExpiry;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate cardExpiry;

    @JsonIgnore
    public CardDetail getCardDetail(){
        return new CardDetail(this.cardNumber,
                this.nameOnCard,
                this.CCV,
                this.cardExpiry);
    }
}