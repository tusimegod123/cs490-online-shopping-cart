package ecommerce.shoppingcartservice.service;

import ecommerce.shoppingcartservice.model.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart getCartItems(int id);

     void addToCart(RequestModel requestModel);


    void deleteCart();

    boolean checkCartExistForUser(Integer id);

    void checkOut(ShoppingCart shoppingCart);
}
