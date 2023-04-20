package com.cs490.shoppingCart.ReportingModule.service;

import com.cs490.shoppingCart.ReportingModule.integration.ShoppingCartApplicationRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportingService {

    @Autowired
    private ShoppingCartApplicationRestClient client;

    public Optional<SalesDTO> getSalesByDays(String days){
        Orders orders = client.getOrders("days",days);
        SalesDTO sales=new SalesDTO();
        sales.setNoOfSales(orders.getOrders().size());
        return Optional.of(sales);
    }

    public  Optional<SalesDTO> getSalesByProduct(String productName){
        Orders orders = client.getOrders("productName",productName);
        SalesDTO sales=new SalesDTO();
        sales.setNoOfSales(orders.getOrders().size());
        return Optional.of(sales);
    }

    public  Optional<SalesDTO> getSalesByCategory(String category){
        Orders orders = client.getOrders("category",category);
        SalesDTO sales=new SalesDTO();
        sales.setNoOfSales(orders.getOrders().size());
        return Optional.of(sales);
    }

    public  Optional<SalesDTO> getSalesByVendor(String vendorId){
        Orders orders = client.getOrders("vendorId",vendorId);
        SalesDTO sales=new SalesDTO();
        sales.setNoOfSales(orders.getOrders().size());
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getAnnulProfitByVendor(String vendorId) {
        String profit = client.getAnnualProfit("vendorId",vendorId);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualProfit(profit);
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getTotalAnnualProfit() {
        String profit = client.getAnnualProfit("total",null);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualProfit(profit);
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getAnnulLossByVendor(String vendorId) {
        String loss = client.getAnnualLoss("vendorId",vendorId);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualLoss(loss);
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getTotalAnnualLoss() {
        String loss = client.getAnnualLoss("total",null);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualLoss(loss);
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getAnnulRevenueByVendor(String vendorId) {
        String revenueDetails = client.getAnnualRevenue("vendorId",vendorId);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualRevenue(revenueDetails);
        return Optional.of(sales);
    }

    public Optional<SalesDTO> getTotalAnnualRevenue() {
        String revenueDetails = client.getAnnualRevenue("total",null);
        SalesDTO sales=new SalesDTO();
        sales.setAnnualRevenue(revenueDetails);
        return Optional.of(sales);
    }

}
