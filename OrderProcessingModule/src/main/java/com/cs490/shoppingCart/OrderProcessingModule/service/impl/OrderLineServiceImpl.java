package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderLineRepository;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;



import com.cs490.shoppingCart.OrderProcessingModule.service.OrderLineService;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    OrderLineRepository cartLineRepository;

    @Autowired
    OrderRepository shoppingCartRepository;



    @Autowired
    OrderService orderService;

    @Override
    public void removeCartLine(int cartId) {
        cartLineRepository.deleteById(cartId);
    }

    @Override
    public boolean checkCartLineExistence(Integer cartId){
        return cartLineRepository.existsById(cartId);
    }

    @Override
    public OrderLine updateCartLine(OrderLine cartLine) {
        OrderLine existingOrderLine = cartLineRepository.findById(cartLine.getId()).get();
        existingOrderLine.setQuantity(cartLine.getQuantity());
        return cartLineRepository.save(existingOrderLine);
    }



}
