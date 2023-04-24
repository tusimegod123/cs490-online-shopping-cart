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

import java.util.Optional;

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
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByCartLinesId(cartId).get();
        CartLine cartLine = cartLineRepository.findById(cartId).get();
        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - cartLine.getPrice());
        cartLineRepository.deleteById(cartId);
        shoppingCart = shoppingCartRepository.findById(shoppingCart.getId()).get();
        if(shoppingCart.getCartLines().size() == 0) //&& shoppingCart.getCartLines().stream().findFirst().get().getId() == cartId)
            shoppingCartRepository.deleteById(shoppingCart.getId());
        else
            shoppingCartRepository.save(shoppingCart);
        //cartLineRepository.deleteById(cartId);
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
