package ecommerce.shoppingcartservice.service;

import ecommerce.shoppingcartservice.model.dto.CartLineRequest;
import ecommerce.shoppingcartservice.model.CartLine;

public interface CartLineService {

    void removeCartLine(Long cartId);

    public boolean checkCartLineExistence(Long cartId);

    CartLine updateCartLine(CartLineRequest cartLineRequest);
}
