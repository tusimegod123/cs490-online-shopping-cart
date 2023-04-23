package com.cs490.shoppingCart.ReportingModule.service;

import com.cs490.shoppingCart.ReportingModule.integration.ShoppingCartApplicationRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportingService {

    @Autowired
    private ShoppingCartApplicationRestClient client;

    public  Optional<SalesDTO> getSales(ReportRequest request){
        OrderList orders = client.getOrders(request);
        SalesDTO sales=new SalesDTO();
        sales.setNoOfSales(orders.getOrders().size());
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getAnnulProfit(ReportRequest request) {
        Double profit = client.getAnnualProfit(request);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualProfit(profit);
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getAnnualLoss(ReportRequest request) {
        Double loss = client.getAnnualLoss(request);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualLoss(loss);
        return Optional.of(sales);
    }


    public Optional<SalesDTO> getAnnualRevenue(ReportRequest request) {
        Double revenueDetails = client.getAnnualRevenue(request);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualRevenue(revenueDetails);
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getReportSummary(ReportRequest request) {
        SalesDTO report = client.getSummary(request);
        return Optional.of(report);
    }

}
