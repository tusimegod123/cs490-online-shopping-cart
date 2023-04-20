package com.cs490.shoppingCart.ProfitSharingModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportRequest {
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long userId;
}
