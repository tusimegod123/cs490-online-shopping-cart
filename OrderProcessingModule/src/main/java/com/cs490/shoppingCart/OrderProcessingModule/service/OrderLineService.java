package com.cs490.shoppingCart.OrderProcessingModule.service;


import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;

public interface OrderLineService {

    void removeCartLine(int cartId);

    public boolean checkCartLineExistence(Integer cartId);

    OrderLine updateCartLine(OrderLine cartLine);
}
