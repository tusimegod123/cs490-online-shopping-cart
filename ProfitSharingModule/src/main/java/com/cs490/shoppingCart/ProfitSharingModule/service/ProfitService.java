package com.cs490.shoppingCart.ProfitSharingModule.service;

import com.cs490.shoppingCart.ProfitSharingModule.dto.ProfitRequest;
import org.springframework.stereotype.Service;

public interface ProfitService {
    public Boolean processProfit(ProfitRequest profitRequest);
}