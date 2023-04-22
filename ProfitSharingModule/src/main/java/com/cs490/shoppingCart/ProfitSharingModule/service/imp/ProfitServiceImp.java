package com.cs490.shoppingCart.ProfitSharingModule.service.imp;
import com.cs490.shoppingCart.ProfitSharingModule.dto.*;
import com.cs490.shoppingCart.ProfitSharingModule.model.Profit;
import com.cs490.shoppingCart.ProfitSharingModule.repository.ProfitRepository;
import com.cs490.shoppingCart.ProfitSharingModule.service.ProfitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

        OrderDTO order = restTemplate.getForObject("http://order-service:8085/api/v1/orders/" + profitRequest.getOrderId(), OrderDTO.class);

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
                vProfit.setProductId(product.getProductID());
                vProfit.setPercentage(80d);
                vProfit.setAmount(vendorShare);
                vProfit.setTransactionId(profitRequest.getTransactionId());
                vProfit.setTransactionNumber(profitRequest.getTransactionNumber());
                vProfit.setTransactionDate(profitRequest.getTransactionDate());

                Profit sProfit = new Profit();
                sProfit.setUserId(systemId);
                sProfit.setProductId(product.getProductID());
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

    @Override
    public Double getProfit(ReportRequest request) {
        List<Profit> profitList;

        if(request.getUserId() != null){
            profitList = profitRepository.findAllByUserIdAndTransactionDateIsBetween(
                    request.getUserId(),
                    Date.from(request.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(request.getToDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        } else {
            profitList = profitRepository.findAllByTransactionDateIsBetween(
                    Date.from(request.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                     Date.from(request.getToDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        }

        AtomicReference<Double> salePrice = new AtomicReference<>(0d);
        Double buyPrice = 0d;
        Set<Long> productIds = new HashSet();

        profitList.stream().filter(p -> p.getUserId() != 0 )
                .forEach( p -> {
                    salePrice.updateAndGet(v -> v + p.getAmount());
                    productIds.add(p.getProductId());
                });

        //connect with product service
        Map<String, Long> queryParams = new HashMap<>();

        for(Long id : productIds){
            queryParams.put("productId", id);
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://product-service:8083/api/v1/products/productDetail");

        for (Map.Entry<String, Long> entry : queryParams.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        String uriString = builder.toUriString();

        try {
            List<ProductResponseDTO> productList = restTemplate.exchange(
                    uriString,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProductResponseDTO>>() {}).getBody();
            System.out.println(productList);

            for (Profit p: profitList) {
                if(p.getUserId() != 0){
                    Optional<ProductResponseDTO> product = productList.stream()
                            .filter(obj -> obj.getProductId() == p.getProductId())
                            .findFirst();
                    if(product.isPresent()){
                        buyPrice += product.get().getItemCost();
                    }
                }
            }

        } catch (Exception ex) {
           logger.error("Error occurred while sending the request to product service!");
        }

        return salePrice.get() - buyPrice;
    }

    @Override
    public Double getRevenue(ReportRequest request) {
        System.out.println(request);

        List<Profit> profitList;

        if(request.getUserId() != null){
            profitList = profitRepository.findAllByUserIdAndTransactionDateIsBetween(
                    request.getUserId(),
                    Date.from(request.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(request.getToDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        } else {
            profitList = profitRepository.findAllByTransactionDateIsBetween(
                    Date.from(request.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            Date.from(request.getToDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        }

        Double revenue = profitList.stream()
                .filter(p -> p.getUserId() != 0)
                .mapToDouble(p -> p.getAmount())
                .sum();

        return revenue;
    }
}
