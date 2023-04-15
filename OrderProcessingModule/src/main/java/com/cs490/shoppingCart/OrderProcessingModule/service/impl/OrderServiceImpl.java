package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.model.*;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.CartLine;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.GuestOrderRequest;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.ShoppingCart;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.User;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderLineRepository;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;
import com.cs490.shoppingCart.OrderProcessingModule.repository.ProductRepository;
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
    private OrderLineRepository orderLineRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    //update
    @Override
    public Order createOrder (ShoppingCart shoppingCart) {

        Set<CartLine> cartLines = shoppingCart.getCartLines();
            Order order = new Order();
            Set<OrderLine> orderLines = cartLines.stream().map(cartLine -> {
                OrderLine orderLine =  new OrderLine(
                    new Product(cartLine.getProduct().getName(),
                            cartLine.getProduct().getCategory(),cartLine.getProduct().getDescription(),
                            cartLine.getProduct().getPrice()), cartLine.getQuantity(),cartLine.getPrice());

                return orderLine;
            }).collect(Collectors.toSet());
            order.setOrderLines(orderLines);
            order.setOrderDate(LocalDateTime.now());
            //order not payed yet or pending order
            order.setOrderStatus(false);
            order.setUserId(shoppingCart.getUserId());
            order.setTotalPrice(shoppingCart.getTotalPrice());
            Order pendingOrder = orderRepository.save(order);
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
        User tempUser = new User();
        ShoppingCart shoppingCart =  guestOrderRequest.getShoppingCart();
        shoppingCart.setUserId(tempUser.getId());
        return createOrder(shoppingCart);
    }

    @Override
    public List<Order> getOrdersForUser(int userId) {
        return orderRepository.findAllByUserIdEquals(userId);
    }


}
