package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderRequestDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.PaymentInfoDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.PaymentRequestDTO;
import com.cs490.shoppingCart.OrderProcessingModule.model.*;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.CartLine;
import com.cs490.shoppingCart.OrderProcessingModule.dto.GuestOrderRequest;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.ShoppingCart;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.UserDTO;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderLineRepository;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    //update
    @Override
    public Order createOrder (OrderRequestDTO orderRequestDTO) {

        Order pendingOrder = createOrderLine(orderRequestDTO.getShoppingCart());
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                orderRequestDTO.getShoppingCart().getUserId(),pendingOrder.getId(),
                pendingOrder.getTotalPrice()*0.15 + pendingOrder.getTotalPrice(),
                new PaymentInfoDTO(
                orderRequestDTO.getPaymentInfoDTO().getCardNumber(),
                orderRequestDTO.getPaymentInfoDTO().getNameOnCard(),
                        orderRequestDTO.getPaymentInfoDTO().getCCV(),
                orderRequestDTO.getPaymentInfoDTO().getCardExpiry())
        );
            // call payment module right here
            return pendingOrder;

    }

    @Override
    public boolean checkOrderExistance(Integer id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Order getOrder(int orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    @Override
    public Order createGuestOrder(GuestOrderRequest guestOrderRequest) {
        // rest template call to use service returns userobject
        UserDTO tempUser = new UserDTO();
        ShoppingCart shoppingCart =  guestOrderRequest.getShoppingCart();
        shoppingCart.setUserId(tempUser.getId());
        Order pendingOrder = createOrderLine(shoppingCart);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(
                tempUser.getId(),pendingOrder.getId(),
                pendingOrder.getTotalPrice()*0.15 + pendingOrder.getTotalPrice(),
                new PaymentInfoDTO(
                        guestOrderRequest.getPaymentInfo().getCardNumber(),
                        guestOrderRequest.getPaymentInfo().getNameOnCard(),
                        guestOrderRequest.getPaymentInfo().getCCV(),
                        guestOrderRequest.getPaymentInfo().getCardExpiry())
        );
        // call payment module right here
        return pendingOrder;
    }


    @Override
    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.findAllByUserIdEquals(userId);
    }

    @Override
    public boolean checkOrderStatusIsNotSuccessful(int orderId) {

            Order order = orderRepository.findById(orderId).get();
            if(order.getOrderStatus() == OrderStatus.OS){
                return false;
            }else{
                order.setOrderStatus(OrderStatus.OS);
                orderRepository.save(order);
                return true;
            }
    }
    private Order createOrderLine(ShoppingCart shoppingCart){

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
        Order pendingOrder = orderRepository.save(order);
        return pendingOrder;
    }



}
