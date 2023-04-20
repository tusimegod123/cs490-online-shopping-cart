package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.dto.*;
import com.cs490.shoppingCart.OrderProcessingModule.model.*;
import com.cs490.shoppingCart.OrderProcessingModule.dto.CartLine;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Order> getOrders() {
        List<Order> orders = orderRepository.findAll();
         return orders;
    }

    //update
    @Override
    public Order createOrder (OrderRequestDTO orderRequestDTO) {

        Order pendingOrder = createOrderLine(orderRequestDTO.getShoppingCart());
        UserDTO userDTO = restTemplate.getForObject("http://localhost:9898/api/v1/users/1", UserDTO.class);
        pendingOrder.setUserInfo(creteUserInfoString(userDTO));
        Order order=orderRepository.save(pendingOrder);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                orderRequestDTO.getShoppingCart().getUserId(),pendingOrder.getId(),
                pendingOrder.getTotalPrice()*0.15 + pendingOrder.getTotalPrice(),
                        orderRequestDTO.getPaymentInfoDTO().getCardNumber(),
                        orderRequestDTO.getPaymentInfoDTO().getNameOnCard(),
                        orderRequestDTO.getPaymentInfoDTO().getCCV(),
                        orderRequestDTO.getPaymentInfoDTO().getCardExpiry()
        );
        String status = restTemplate.postForObject("http://localhost:8086/api/v1/payments/payOrder",paymentRequestDTO,String.class);
        updateOrderStatus(status,order);
        return pendingOrder;

    }

    @Override
    public boolean checkOrderExistance(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @Override
    public OrderDTO createGuestOrder(GuestOrderRequest guestOrderRequest) {

        Set<Role> roles = new HashSet<>();
        Role role =  new Role("Guest");
        roles.add(role);
        UserDTO request =  new UserDTO(guestOrderRequest.getUserInfo().getName(),guestOrderRequest.getUserInfo().getEmail(), guestOrderRequest.getUserInfo().getTelephoneNumber(),roles);
        UserDTO tempUser = restTemplate.postForObject("http://localhost:9898/api/v1/users/register",request, UserDTO.class);
        //UserDTOx tempUser = new UserDTOx();
        ShoppingCartDTO shoppingCart =  guestOrderRequest.getShoppingCart();
        shoppingCart.setUserId(tempUser.getUserId());
        Order pendingOrder = createOrderLine(shoppingCart);
        pendingOrder.setUserInfo(creteUserInfoString(guestOrderRequest.getUserInfo()));
        Order order = orderRepository.save(pendingOrder);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                tempUser.getUserId(),pendingOrder.getId(),
                pendingOrder.getTotalPrice()*0.15 + pendingOrder.getTotalPrice(),
                        guestOrderRequest.getPaymentInfo().getCardNumber(),
                        guestOrderRequest.getPaymentInfo().getNameOnCard(),
                        guestOrderRequest.getPaymentInfo().getCCV(),
                        guestOrderRequest.getPaymentInfo().getCardExpiry()
        );
        String status = restTemplate.postForObject("http://localhost:8086/api/v1/payments/payOrder",paymentRequestDTO,String.class);
        updateOrderStatus(status,order);
        return modelMapper.map(order,OrderDTO.class);
    }


    @Override
    public List<Order> getOrdersForUser(Long userId) {
        return orderRepository.findAllByUserIdEquals(userId);
    }

    @Override
    public boolean checkOrderStatusIsNotSuccessful(Long orderId) {

        Order order = orderRepository.findById(orderId).get();
        if(order.getOrderStatus() == OrderStatus.OS){
            return false;
        }else{
            order.setOrderStatus(OrderStatus.OS);
            orderRepository.save(order);
            return true;
        }
    }

    @Override
    public List<OrderDTO> getAllOrdersForReport(LocalDate initalDate, LocalDate finalDate, Long vendorId) {
        List<Order> orders = new ArrayList<>();
        //Set<Order> orderIds = new HashSet<>();
        orders = orderRepository.findAllByOrderDateBetween(initalDate.atStartOfDay() ,finalDate.atStartOfDay());
//        orders.forEach(order -> order.getOrderLines().stream().anyMatch(
//                orderLine -> createProductDTO(orderLine.getProductInfo()).getUserId().equals(vendorId)));
        if(vendorId != null){
//           for(Order or : orders){
//                for(OrderLine ol : or.getOrderLines()){
//                    System.out.println(createProductDTO(ol.getProductInfo()).getUserId().equals(vendorId));
//                    if(createProductDTO(ol.getProductInfo()).getUserId().intValue() == vendorId.intValue())
//                        orderIds.add(or);
//                }
//           }
             return orders.stream().filter(
                    order -> order.getOrderLines().stream().anyMatch(
                            orderLine -> createProductDTO(orderLine.getProductInfo()).getUserId().equals(vendorId)))
                     .map(order -> modelMapper.map(order,OrderDTO.class))
                .collect(Collectors.toList());
        }
        return orders.stream().map(order -> modelMapper.map(order,OrderDTO.class)).collect(Collectors.toList());
                //orderIds.stream().toList();
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

    private void updateOrderStatus(String status,Order order){
        if(status.contains("TS"))
            order.setOrderStatus(OrderStatus.OS);
        else if(status.contains("TF"))
            order.setOrderStatus(OrderStatus.OF);
        orderRepository.save(order);
    }

}