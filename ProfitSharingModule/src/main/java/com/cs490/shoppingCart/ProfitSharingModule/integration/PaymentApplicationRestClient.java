package com.cs490.shoppingCart.ProfitSharingModule.integration;

import com.cs490.shoppingCart.ProfitSharingModule.dto.PaymentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PaymentApplicationRestClient {

    @Value("${payment.management.url}")
    private String paymentUrl;

    RestTemplate restTemplate = new RestTemplate();
    Logger logger= LoggerFactory.getLogger(PaymentApplicationRestClient.class);

    public PaymentDTO getPayment(Long paymentId) {
        PaymentDTO payment = null;
        try{
            payment = restTemplate.getForObject(paymentUrl+ "/" + paymentId, PaymentDTO.class);
        }catch(Exception e){
            logger.error("Requested operation failed, "+ e.getMessage());
        }
        return payment;
    }
}
