package com.cs490.shoppingCart.ProfitSharingModule.service.imp;
import com.cs490.shoppingCart.ProfitSharingModule.dto.OrderDTO;
import com.cs490.shoppingCart.ProfitSharingModule.dto.OrderLineDTO;
import com.cs490.shoppingCart.ProfitSharingModule.dto.ProductDTO;
import com.cs490.shoppingCart.ProfitSharingModule.dto.ProfitRequest;
import com.cs490.shoppingCart.ProfitSharingModule.model.Profit;
import com.cs490.shoppingCart.ProfitSharingModule.repository.ProfitRepository;
import com.cs490.shoppingCart.ProfitSharingModule.service.ProfitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class ProfitServiceImp implements ProfitService {

    @Autowired
    private ProfitRepository profitRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger =
            LoggerFactory.getLogger(ProfitService.class);

    @Override
    @Transactional
    public Boolean processProfit(ProfitRequest profitRequest) {
        System.out.println(profitRequest);

        Long vendorId = -1l;
        Long systemId = 0l;

        OrderDTO order = restTemplate.getForObject("http://localhost:8093/orders/" + profitRequest.getOrderId(), OrderDTO.class);

        for (OrderLineDTO orderLine: order.getOrderLines()) {
            Double totalPrice = orderLine.getPrice();
            Double vendorShare = totalPrice * 0.8;
            Double systemShare = totalPrice * 0.2;

            ObjectMapper mapper = new ObjectMapper();
            try {
                ProductDTO product = mapper.readValue(orderLine.getProductInfo(), ProductDTO.class);
                vendorId = product.getUserId();

                Profit vProfit = new Profit();
                vProfit.setUserId(vendorId);
                vProfit.setProductId(product.getId());
                vProfit.setPercentage(80d);
                vProfit.setAmount(vendorShare);
                vProfit.setTransactionId(profitRequest.getTransactionId());
                vProfit.setTransactionNumber(profitRequest.getTransactionNumber());
                vProfit.setTransactionDate(profitRequest.getTransactionDate());

                Profit sProfit = new Profit();
                sProfit.setUserId(systemId);
                sProfit.setProductId(product.getId());
                sProfit.setPercentage(20d);
                sProfit.setAmount(systemShare);
                sProfit.setTransactionId(profitRequest.getTransactionId());
                sProfit.setTransactionNumber(profitRequest.getTransactionNumber());
                sProfit.setTransactionDate(profitRequest.getTransactionDate());

                profitRepository.save(sProfit);
                profitRepository.save(vProfit);

            } catch (JsonProcessingException e) {
                logger.error("Failed to parse product information");
                logger.info(orderLine.getProductInfo());
                throw new RuntimeException(e);
            }
        }

        return true;
    }
}
