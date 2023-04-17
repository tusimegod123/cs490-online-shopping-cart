package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderLineRepository;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;

import com.cs490.shoppingCart.OrderProcessingModule.repository.ProductRepository;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderLineService;
import com.cs490.shoppingCart.OrderProcessingModule.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Line;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    OrderLineRepository cartLineRepository;

    @Autowired
    OrderRepository shoppingCartRepository;

    @Autowired
    ProductRepository productRepository;

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

//    @Override
//    public void addCartLine(RequestModel requestModel) {
//       // if(checkCartExistForUser(requestModel.getAccountId())){
//            ShoppingCart shoppingCart = shoppingCartService.getCartItems(requestModel.getAccountId());
//
//            Optional<CartLine> cartLineEx = shoppingCart.getCartLines().stream().filter(cartLine -> cartLine.getProduct().getId().equals(requestModel.getProductId()) && shoppingCart.getStatus() == false ).findAny();
//
//            if(cartLineEx.isPresent()){
//
//                CartLine cartLineExisting = cartLineEx.get();
//                cartLineExisting.setQuantity(cartLineExisting.getQuantity()+ requestModel.getQuantity());
//                Set<CartLine> cartLineSet = new HashSet<>(shoppingCart.getCartLines());
//                cartLineSet.add(cartLineExisting);
//                shoppingCart.setCartLines(cartLineSet);
//                shoppingCartRepository.save(shoppingCart);
//
//            }else{
//
//                CartLine cartLine = new CartLine();
//                cartLine.setQuantity(requestModel.getQuantity());
//                cartLine.setProduct(productRepository.findById(requestModel.getProductId()).get());
//                Set<CartLine> existingLines = new HashSet<>(shoppingCart.getCartLines());
//                existingLines.add(cartLine);
//                shoppingCart.setCartLines(existingLines);
//                shoppingCartRepository.save(shoppingCart);
//
//            }
//
//    }


}
