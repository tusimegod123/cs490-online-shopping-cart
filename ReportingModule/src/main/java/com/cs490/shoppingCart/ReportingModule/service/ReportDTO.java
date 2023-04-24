package com.cs490.shoppingCart.ReportingModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ReportDTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
	private int totalRevenue;
    private double rate;
    private int numberOfTransactions;
    private int aov;
}
