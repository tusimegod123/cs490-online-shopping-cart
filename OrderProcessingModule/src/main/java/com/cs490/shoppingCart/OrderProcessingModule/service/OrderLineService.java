package com.cs490.shoppingCart.OrderProcessingModule.service;


import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;

public interface OrderLineService {

    void removeCartLine(Long cartId);

    public boolean checkCartLineExistence(Long cartId);

    OrderLine updateCartLine(OrderLine cartLine);
}
