package com.cs490.shoppingCart.OrderProcessingModule.service;


import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.GuestOrderRequest;
import com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects.ShoppingCart;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

     Order createOrder(ShoppingCart shoppingCart);



    boolean checkOrderExistance(Integer id);


    Order getOrder(int orderId);

    Order createGuestOrder(GuestOrderRequest guestOrderRequest);

    List<Order> getOrdersForUser(int userId);
}
