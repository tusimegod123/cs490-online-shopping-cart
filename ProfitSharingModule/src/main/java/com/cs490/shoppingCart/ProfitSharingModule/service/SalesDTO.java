package com.cs490.shoppingCart.ProfitSharingModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {
    private int noOfSales;
    private Double annualProfit;
    private Double annualLoss;
    private Double annualRevenue;
}