package com.cs490.shoppingCart.PaymentModule.DTO;

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
    private PaymentType paymentType;
    private Double amount;

    private String cardNumber;
    private String name;
    private String CCV ;
    private Date cardExpiry;

    public CardDetailDTO getCardDetail(){
        return new CardDetailDTO(this.cardNumber,
                this.name,
                this.CCV,
                this.cardExpiry);
    }
}