package com.cs490.shoppingCart.PaymentModule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

//    @Temporal(TemporalType.DATE)
//    private Date cardExpiry;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate cardExpiry;

    private Boolean cardMode = true;
}
