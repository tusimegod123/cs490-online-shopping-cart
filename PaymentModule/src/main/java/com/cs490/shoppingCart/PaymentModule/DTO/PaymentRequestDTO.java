package com.cs490.shoppingCart.PaymentModule.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private Integer orderId;

    private Integer userId;
//    private PaymentType paymentType;
    private Double amount;

    private String cardNumber;
    private String nameOnCard;
    private String CCV ;
    private Date cardExpiry;

    @JsonIgnore
    public CardDetailDTO getCardDetail(){
        return new CardDetailDTO(this.cardNumber,
                this.nameOnCard,
                this.CCV,
                this.cardExpiry);
    }
}