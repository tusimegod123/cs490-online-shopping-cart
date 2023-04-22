package com.cs490.shoppingCart.NotificationModule.integration;

import com.cs490.shoppingCart.NotificationModule.service.AddressDTO;
import com.cs490.shoppingCart.NotificationModule.service.OrderDTO;
import com.cs490.shoppingCart.NotificationModule.service.UserDTO;
//import com.cs490.shoppingCart.NotificationModule.util.AppInfo;
import com.cs490.shoppingCart.NotificationModule.util.AppInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class ShoppingCartApplicationRestClient {

    @Autowired
    private AppInfo appInfo;

    RestTemplate restTemplate = new RestTemplate();
    Logger logger = LoggerFactory.getLogger(ShoppingCartApplicationRestClient.class);

    public UserDTO getUser(Long userId) {
        UserDTO user = null;
        try {
            user = restTemplate.getForObject(appInfo.getUserUrl() + "/" + userId, UserDTO.class);
        } catch (Exception e) {
            logger.error("Resquested operation failed, " + e.getMessage());
        }
        return user;
    }

    public OrderDTO getOrder(String orderId) {
        OrderDTO order = null;
        try {
            order = restTemplate.getForObject(appInfo.getOrderUrl() + "/" + orderId, OrderDTO.class);
        } catch (Exception e) {
            logger.error("Resquested operation failed, " + e.getMessage());
        }
        return order;
    }
}
