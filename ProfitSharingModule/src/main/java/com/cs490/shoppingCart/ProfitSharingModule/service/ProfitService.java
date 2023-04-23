package com.cs490.shoppingCart.ProfitSharingModule.service;

import com.cs490.shoppingCart.ProfitSharingModule.dto.ProfitRequest;
import com.cs490.shoppingCart.ProfitSharingModule.dto.ReportRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;

public interface ProfitService {
    public Boolean processProfit(ProfitRequest profitRequest) throws ParseException;

    Double getProfit(ReportRequest request);

    Double getRevenue(ReportRequest request);
}
