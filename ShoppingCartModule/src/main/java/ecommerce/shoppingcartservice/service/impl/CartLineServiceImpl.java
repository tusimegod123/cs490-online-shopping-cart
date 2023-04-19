package ecommerce.shoppingcartservice.service.impl;

import ecommerce.shoppingcartservice.model.dto.CartLineRequest;
import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import ecommerce.shoppingcartservice.repository.CartLineRepository;
import ecommerce.shoppingcartservice.repository.ShoppingCartRepository;
import ecommerce.shoppingcartservice.service.CartLineService;
import ecommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartLineServiceImpl implements CartLineService {

    @Autowired
    CartLineRepository cartLineRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;


    @Autowired
    ShoppingCartService shoppingCartService;

    @Override
    public void removeCartLine(Long cartId) {
        cartLineRepository.deleteById(cartId);
    }

    @Override
    public boolean checkCartLineExistence(Long cartId){
        return cartLineRepository.existsById(cartId);
    }

    @Override
    public CartLine updateCartLine(CartLineRequest cartLineRequest) {

        CartLine existingCartLine = cartLineRepository.findById(cartLineRequest.getId()).get();
        existingCartLine.setQuantity(cartLineRequest.getQuantity());
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByCartLinesId(cartLineRequest.getId()).get();
        shoppingCart.setTotalPrice((shoppingCart.getTotalPrice() - existingCartLine.getPrice()) +cartLineRequest.getProductPrice() * cartLineRequest.getQuantity());
        existingCartLine.setPrice(cartLineRequest.getProductPrice() * cartLineRequest.getQuantity());
        shoppingCartRepository.save(shoppingCart);
        return cartLineRepository.save(existingCartLine);
    }




}
