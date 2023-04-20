package com.cs490.shoppingCart.ReportingModule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportRequest {
//@Temporal(TemporalType.DATE)
private LocalDate fromDate;
//@Temporal(TemporalType.DATE)
private LocalDate toDate;
private Long userId;
}