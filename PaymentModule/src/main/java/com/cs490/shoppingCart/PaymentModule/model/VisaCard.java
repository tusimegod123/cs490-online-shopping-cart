package com.cs490.shoppingCart.PaymentModule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VisaCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(unique=true)
    private String cardNumber;

    private String name;
    private String CCV ;
    private Double cardValue;
    private Double currentValue;
    private Date cardExpiry;
    private Boolean cardMode = true;
}
