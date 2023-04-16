package com.cs490.shoppingCart.ReportingModule.integration;
import com.cs490.shoppingCart.ReportingModule.service.Orders;
import com.cs490.shoppingCart.ReportingModule.service.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShoppingCartApplicationRestClient {

    @Value("${order.management.url}")
    private String orderUrl;

    @Value("${user.management.url}")
    private String userUrl;

    @Value("${profit.sharing.management.url}")
    private String profitSharingUrl;

    RestTemplate restTemplate = new RestTemplate();
    Logger logger= LoggerFactory.getLogger(ShoppingCartApplicationRestClient.class);

    public Orders getOrders(String searchType, String searchValue) {
        Orders orders=null;
        try{
            if(searchType.equals("days")){
                orders =restTemplate.getForObject(orderUrl+"?days={days}",Orders.class,searchValue);
            }
            if(searchType.equals("productName")){
                orders =restTemplate.getForObject(orderUrl+"?productName={productName}",Orders.class,searchValue);
            }
            if(searchType.equals("category")){
                orders =restTemplate.getForObject(orderUrl+"?category={category}",Orders.class,searchValue);
            }

            if(searchType.equals("vendorId")){
                orders =restTemplate.getForObject(orderUrl+"?vendorId={vendorId}",Orders.class,searchValue);
            }
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return orders;
    }


    public String getAnnualProfit(String searchType, String searchValue) {
        String profitValue=null;
        try{
            if(searchType.equals("vendorId")){
                profitValue =restTemplate.getForObject(profitSharingUrl+"/profit?vendorId={vendorId}",String.class,searchValue);
            }
            else{
                profitValue =restTemplate.getForObject(profitSharingUrl+"/profit",String.class);
            }

        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return profitValue;
    }

    public String getAnnualLoss(String searchType, String searchValue) {
        String lossValue=null;
        try{
            if(searchType.equals("vendorId")){
                lossValue =restTemplate.getForObject(profitSharingUrl+"/loss?vendorId={vendorId}",String.class,searchValue);
            }
            else{
                lossValue =restTemplate.getForObject(profitSharingUrl+"/loss",String.class);
            }

        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return lossValue;
    }

    public String getAnnualRevenue(String searchType, String searchValue) {
        String revenueValue=null;
        try{
            if(searchType.equals("vendorId")){
                revenueValue =restTemplate.getForObject(profitSharingUrl+"/revenue?vendorId={vendorId}",String.class,searchValue);
            }
            else{
                revenueValue =restTemplate.getForObject(profitSharingUrl+"/revenue",String.class);
            }

        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return revenueValue;
    }

    public UserDTO getUser(String userId) {
        UserDTO user=null;
        try{
            user =restTemplate.getForObject(userUrl+ "/" + userId, UserDTO.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return user;
    }

}
