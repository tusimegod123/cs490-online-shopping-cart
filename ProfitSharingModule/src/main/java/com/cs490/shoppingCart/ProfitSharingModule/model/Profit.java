package com.cs490.shoppingCart.ProfitSharingModule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long paymentId;
    private Double percentage;
    private Double amount;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
}
