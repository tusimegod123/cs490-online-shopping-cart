package com.cs490.shoppingCart.ReportingModule.integration;
import com.cs490.shoppingCart.ReportingModule.service.OrderList;
import com.cs490.shoppingCart.ReportingModule.service.ReportRequest;
import com.cs490.shoppingCart.ReportingModule.service.SalesDTO;
import com.cs490.shoppingCart.ReportingModule.service.UserDTO;
import com.cs490.shoppingCart.ReportingModule.util.AppInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShoppingCartApplicationRestClient {

    @Autowired
    private AppInfo appInfo;

    RestTemplate restTemplate = new RestTemplate();
    Logger logger= LoggerFactory.getLogger(ShoppingCartApplicationRestClient.class);

    public OrderList getOrders(ReportRequest request) {
        OrderList orders=null;
        try{

            String requestParam = "?initialDate="+ request.getFromDate() + "&finalDate=" +request.getToDate() + "&vendorId=";

            if(request.getUserId() != null){
                requestParam += request.getUserId();
            }

            orders =restTemplate.getForObject(appInfo.getOrderUrl() + "/reports" + requestParam, OrderList.class);

        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return orders;
    }


    public Double getAnnualProfit(ReportRequest request) {
        Double profitValue=null;
        try{
            profitValue = restTemplate.postForObject(appInfo.getProfitUrl()+"/getProfit", request, Double.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return profitValue;
    }

    public Double getAnnualLoss(ReportRequest request) {
        Double lossValue=null;
        try{
            lossValue = restTemplate.postForObject(appInfo.getProfitUrl()+"/getLoss", request, Double.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return lossValue;
    }

    public Double getAnnualRevenue(ReportRequest request) {
        Double revenueValue=null;
        try{
            revenueValue = restTemplate.postForObject(appInfo.getProfitUrl()+"/getRevenue", request, Double.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return revenueValue;
    }

    public SalesDTO getSummary(ReportRequest request) {
        SalesDTO report=null;
        try{
            report = restTemplate.postForObject(appInfo.getProfitUrl()+"/summary", request, SalesDTO.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return report;
    }

    public UserDTO getUser(String userId) {
        UserDTO user=null;
        try{
            user =restTemplate.getForObject(appInfo.getUserUrl()+ "/" + userId, UserDTO.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return user;
    }

}
