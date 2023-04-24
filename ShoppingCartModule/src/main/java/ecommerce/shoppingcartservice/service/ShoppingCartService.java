package ecommerce.shoppingcartservice.service;

import ecommerce.shoppingcartservice.model.dto.RequestModel;
import ecommerce.shoppingcartservice.model.dto.ShoppingCartDTO;

public interface ShoppingCartService {

    ShoppingCartDTO getCartItems(Long id);

     ShoppingCartDTO addToCart(RequestModel requestModel);


    void deleteCart();

    boolean checkCartExistForUser(Long id);

    ShoppingCartDTO checkOut(Long id);

    boolean checkCartExistance(Long cartId);
}
