package ecommerce.shoppingcartservice.service;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart getCartItems(int id);

     void addToCart(RequestModel requestModel);


    void deleteCart();

    boolean checkCartExistForUser(Integer id);
}
