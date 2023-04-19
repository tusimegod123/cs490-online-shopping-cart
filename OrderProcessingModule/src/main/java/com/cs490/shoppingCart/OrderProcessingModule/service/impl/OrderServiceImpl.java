package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.dto.*;
import com.cs490.shoppingCart.OrderProcessingModule.model.*;
import com.cs490.shoppingCart.OrderProcessingModule.dto.CartLine;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
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
        //UserDTOx userDTOx = new UserDTOx();
        pendingOrder.setUserInfo(creteUserInfoString(userDTO));
        orderRepository.save(pendingOrder);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                orderRequestDTO.getShoppingCart().getUserId(),pendingOrder.getId(),
                pendingOrder.getTotalPrice()*0.15 + pendingOrder.getTotalPrice(),
                        orderRequestDTO.getPaymentInfoDTO().getCardNumber(),
                        orderRequestDTO.getPaymentInfoDTO().getNameOnCard(),
                        orderRequestDTO.getPaymentInfoDTO().getCCV(),
                        orderRequestDTO.getPaymentInfoDTO().getCardExpiry()
        );

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

    private void updateOrderStatus(String status,Order order){
        if(status.equalsIgnoreCase("TS"))
            order.setOrderStatus(OrderStatus.OS);
        else if(status.equalsIgnoreCase("TF"))
            order.setOrderStatus(OrderStatus.OF);
        orderRepository.save(order);
    }

}