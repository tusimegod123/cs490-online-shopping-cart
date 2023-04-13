package ecommerce.shoppingcartservice.service;

import ecommerce.shoppingcartservice.model.CartLine;

public interface CartLineService {

    void removeCartLine(int cartId);

    public boolean checkCartLineExistence(Integer cartId);

    CartLine updateCartLine(CartLine cartLine);
}
