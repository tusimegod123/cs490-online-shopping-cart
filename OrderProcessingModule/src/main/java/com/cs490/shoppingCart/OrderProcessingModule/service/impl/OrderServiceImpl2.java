package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.dto.*;
import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;
import com.cs490.shoppingCart.OrderProcessingModule.model.OrderStatus;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl2 implements OrderService2 {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${USER_SERVICE_URL:user-service:8082}")
    private String userServiceUrl;

    @Value("${PAYMENT_SERVICE_URL:payment-service:8086}")
    private String paymentServiceUrl;

    @Value("${SHOPPINGCART_SERVICE_URL:shoppingcart-service:8084}")
    private String shoppingCartServiceUrl;




    //update
    @Override
    public Order createOrder (OrderRequestDT orderRequestDTO) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        try {
            shoppingCartDTO = restTemplate.getForObject("http://" + shoppingCartServiceUrl + "/api/v1/cart/" + orderRequestDTO.getUserId(), ShoppingCartDTO.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
                throw new RuntimeException("Sorry! Your shopping cart is empty please add product to your Shopping cart");
        }
        Order pendingOrder = createOrderLine(shoppingCartDTO);
        System.out.println(shoppingCartDTO);
        UserDTO userDTO = restTemplate.getForObject("http://"+userServiceUrl+"/api/v1/users/" + shoppingCartDTO.getUserId(), UserDTO.class);
        System.out.println(userDTO);
        pendingOrder.setUserInfo(creteUserInfoString(userDTO));
        Order order=orderRepository.save(pendingOrder);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                orderRequestDTO.getUserId(),pendingOrder.getId(),
                pendingOrder.getTotalPrice()*0.15 + pendingOrder.getTotalPrice(),
                orderRequestDTO.getPaymentInfoDTO().getCardNumber(),
                orderRequestDTO.getPaymentInfoDTO().getNameOnCard(),
                orderRequestDTO.getPaymentInfoDTO().getCCV(),
                orderRequestDTO.getPaymentInfoDTO().getCardExpiry()
        );

        String status = restTemplate.postForObject("http://"+paymentServiceUrl+"/api/v1/payments/payOrder",paymentRequestDTO,String.class);
        updateOrderStatus(status,order,shoppingCartDTO.getId());

        return pendingOrder;

    }



    private Order createOrderLine(ShoppingCartDTO shoppingCart){

        Set<CartLine> cartLines = shoppingCart.getCartLines();
        Order order = new Order();
        Set<OrderLine> orderLines = cartLines.stream().map(cartLine -> {
            OrderLine orderLine =  new OrderLine();
            orderLine.setPrice(cartLine.getPrice());
            orderLine.setQuantity(cartLine.getQuantity());
            orderLine.setProductInfo(cartLine.getProductInfo());
            return orderLine;
        }).collect(Collectors.toSet());
        order.setOrderLines(orderLines);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OP);
        order.setUserId(shoppingCart.getUserId());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        return order;
    }
    private String creteUserInfoString(UserDTO userDTO){
        String userInfo = "";
        try {
            ObjectMapper ob = new ObjectMapper();
            userInfo =  ob.writeValueAsString(userDTO);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return userInfo;
    }
    private ProductDTO createProductDTO(String productDTO){
        ProductDTO productDTO1 = new ProductDTO();
        try {
            ObjectMapper ob = new ObjectMapper();
            productDTO1 = ob.readValue(productDTO,ProductDTO.class);
        }catch (RuntimeException ex){
            ex.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return productDTO1;
    }

    private void updateOrderStatus(String status,Order order,Long cartId){
        if(status.contains("TS")){
            order.setOrderStatus(OrderStatus.OS);
            restTemplate.postForObject("http://" + shoppingCartServiceUrl + "/api/v1/cart/" + cartId+"/checkout", null,ShoppingCartDTO.class);

        }
        else if(status.contains("TF"))
            order.setOrderStatus(OrderStatus.OF);
        orderRepository.save(order);
    }
}
