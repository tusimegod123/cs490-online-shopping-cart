package com.cs490.shoppingCart.ProfitSharingModule.service;

import com.cs490.shoppingCart.ProfitSharingModule.dto.ProfitRequest;
import com.cs490.shoppingCart.ProfitSharingModule.dto.ReportRequest;


public interface ProfitService {
    public Boolean processProfit(ProfitRequest profitRequest);

    Double getProfit(ReportRequest request);

    Double getRevenue(ReportRequest request);
}
