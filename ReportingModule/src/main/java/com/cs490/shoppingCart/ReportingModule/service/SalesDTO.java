package com.cs490.shoppingCart.ReportingModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {
    private int noOfSales;
    private String annualProfit;
    private String annualLoss;
    private String annualRevenue;
}
