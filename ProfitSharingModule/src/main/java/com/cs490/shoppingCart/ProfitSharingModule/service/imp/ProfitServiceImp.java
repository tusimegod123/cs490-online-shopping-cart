package com.cs490.shoppingCart.ProfitSharingModule.service.imp;

import com.cs490.shoppingCart.ProfitSharingModule.repository.ProfitRepository;
import com.cs490.shoppingCart.ProfitSharingModule.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;

public class ProfitServiceImp implements ProfitService {

    @Autowired
    private ProfitRepository profitRepository;


    public Double getAnnualRevenueForSystem() {
        return 0.0;
    }

//    public List<Payment> getAllPaymentList() {
//
//    }
 }
