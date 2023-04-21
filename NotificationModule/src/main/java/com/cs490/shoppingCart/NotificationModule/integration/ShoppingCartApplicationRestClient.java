package com.cs490.shoppingCart.NotificationModule.integration;

import com.cs490.shoppingCart.NotificationModule.service.AddressDTO;
import com.cs490.shoppingCart.NotificationModule.service.OrderDTO;
import com.cs490.shoppingCart.NotificationModule.service.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class ShoppingCartApplicationRestClient {

    @Value("${order.management.url}")
    private String orderUrl;

    @Value("${userUrl}")
    private String userUrl = "http://localhost:8082/api/v1/users";

    RestTemplate restTemplate = new RestTemplate();
    Logger logger= LoggerFactory.getLogger(ShoppingCartApplicationRestClient.class);

    public UserDTO getUser(Long userId) {
        UserDTO user=restTemplate.getForObject(userUrl+ "/" + userId, UserDTO.class);;
        try{
            if (user == null){
                System.out.println("User is null");
            }
        }catch(Exception e){
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return user;
    }

    public OrderDTO getOrder(String orderId) {
        OrderDTO order=null;
        try{
            order =restTemplate.getForObject(orderUrl+ "/" + orderId, OrderDTO.class);
        }catch(Exception e){
            logger.error("Resquested operation failed, "+ e.getMessage());
        }
        return order;
    }
}
