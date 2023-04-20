package com.cs490.shoppingCart.ReportingModule.integration;
import com.cs490.shoppingCart.ReportingModule.service.OrderList;
import com.cs490.shoppingCart.ReportingModule.service.ReportRequest;
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

    public OrderList getOrders(ReportRequest request) {
        OrderList orders=null;
        try{
            orders =restTemplate.getForObject(orderUrl+"?vendorId={vendorId}?&fromDate={fromDate}?&toDate={toDate}", OrderList.class,request.getFromDate(),
                    request.getToDate(),request.getUserId());
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return orders;
    }


    public Double getAnnualProfit(ReportRequest request) {
        Double profitValue=null;
        try{
            profitValue = restTemplate.postForObject(profitSharingUrl, request, Double.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return profitValue;
    }

    public Double getAnnualLoss(ReportRequest request) {
        Double lossValue=null;
        try{
            lossValue = restTemplate.postForObject(profitSharingUrl, request, Double.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return lossValue;
    }

    public Double getAnnualRevenue(ReportRequest request) {
        Double revenueValue=null;
        try{
            revenueValue = restTemplate.postForObject(profitSharingUrl, request, Double.class);
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
