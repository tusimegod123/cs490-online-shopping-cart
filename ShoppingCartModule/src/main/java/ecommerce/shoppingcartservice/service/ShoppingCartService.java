package ecommerce.shoppingcartservice.service;

import ecommerce.shoppingcartservice.dto.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart getCartItems(int id);

     ShoppingCart addToCart(RequestModel requestModel);


    void deleteCart();

    boolean checkCartExistForUser(Integer id);

    ShoppingCart checkOut(int id);

    boolean checkCartExistance(int cartId);
}
