package ecommerce.shoppingcartservice.service.impl;

import ecommerce.shoppingcartservice.dto.CartLineRequest;
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
    public void removeCartLine(int cartId) {
        cartLineRepository.deleteById(cartId);
    }

    @Override
    public boolean checkCartLineExistence(Integer cartId){
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
