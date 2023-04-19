package com.cs490.shoppingCart.PaymentModule.model;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.DTO.NotificationRequest;
import com.cs490.shoppingCart.PaymentModule.DTO.PaymentType;
import com.cs490.shoppingCart.PaymentModule.DTO.ProfitShareRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userId;
    private Long orderId;
    private String cardNumber;
    private Double cardBalance;
    private Double transactionValue;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String transactionNumber;

    //@Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @JsonIgnore
    public ProfitShareRequest getProfitSharingRequest(){
        return new ProfitShareRequest(this.Id, this.orderId,
                this.transactionValue,
                this.transactionNumber,
                this.transactionDate);
    }

    @JsonIgnore
    public NotificationRequest getNotificationRequest(){
        return new NotificationRequest(this.userId, this.orderId,
                this.transactionValue,
                this.transactionNumber,
                this.transactionDate,
                this.paymentType,
                this.transactionType);
    }

}

