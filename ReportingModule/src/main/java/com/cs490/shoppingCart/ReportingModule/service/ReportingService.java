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
        //Some processing here
        return Optional.empty();
    }

    public  Optional<SalesDTO> getSalesByProduct(String productName){
        Orders orders = client.getOrders("productName",productName);
        //Some processing here
        return Optional.empty();
    }

    public  Optional<SalesDTO> getSalesByCategory(String category){
        Orders orders = client.getOrders("category",category);
        //Some processing here
        return Optional.empty();
    }

    public  Optional<SalesDTO> getSalesByVendor(String vendorId){
        Orders orders = client.getOrders("vendorId",vendorId);
        //Some processing here
        return Optional.empty();
    }

    public Optional<SalesDTO> getAnnulProfitByVendor(String vendorId) {
        String revenueDetails = client.getAnnualProfit("vendorId",vendorId);
        //some processing here
        return Optional.empty();
    }

    public Optional<SalesDTO> getTotalAnnualProfit() {
        String revenueDetails = client.getAnnualProfit("total",null);
        //some processing here
        return Optional.empty();
    }

    public Optional<SalesDTO> getAnnulLossByVendor(String vendorId) {
        String revenueDetails = client.getAnnualLoss("vendorId",vendorId);
        //some processing here
        return Optional.empty();
    }

    public Optional<SalesDTO> getTotalAnnualLoss() {
        String revenueDetails = client.getAnnualLoss("total",null);
        //some processing here
        return Optional.empty();
    }

    public Optional<SalesDTO> getAnnulRevenueByVendor(String vendorId) {
        String revenueDetails = client.getAnnualRevenue("vendorId",vendorId);
        //some processing here
        return Optional.empty();
    }

    public Optional<SalesDTO> getTotalAnnualRevenue() {
        String revenueDetails = client.getAnnualRevenue("total",null);
        //some processing here
        return Optional.empty();
    }

}
