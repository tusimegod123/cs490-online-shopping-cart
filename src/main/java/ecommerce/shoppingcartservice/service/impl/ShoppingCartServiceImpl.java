package ecommerce.shoppingcartservice.service.impl;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import ecommerce.shoppingcartservice.repository.CartLineRepository;
import ecommerce.shoppingcartservice.repository.ProductRepository;
import ecommerce.shoppingcartservice.repository.ShoppingCartRepository;
import ecommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartLineRepository cartLineRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public ShoppingCart getCartItems(int id) {
        return shoppingCartRepository.findShoppingCartByAccountIdEqualsAndStatusEquals(id,false);
    }

    //update
    @Override
    public void addToCart(RequestModel requestModel) {

        if(checkCartExistForUser(requestModel.getAccountId())){
            ShoppingCart shoppingCart = getCartItems(requestModel.getAccountId());

            Optional<CartLine> cartLineEx = shoppingCart.getCartLines().stream().filter(cartLine -> cartLine.getProduct().getId().equals(requestModel.getProductId()) && shoppingCart.getStatus() == false ).findAny();

            if(cartLineEx.isPresent()){

                CartLine cartLineExisting = cartLineEx.get();
                cartLineExisting.setQuantity(cartLineExisting.getQuantity()+ requestModel.getQuantity());
                Set<CartLine> cartLineSet = new HashSet<>(shoppingCart.getCartLines());
                cartLineSet.add(cartLineExisting);
                shoppingCart.setCartLines(cartLineSet);
                shoppingCartRepository.save(shoppingCart);

            }else{

                CartLine cartLine = new CartLine();
                cartLine.setQuantity(requestModel.getQuantity());
                cartLine.setProduct(productRepository.findById(requestModel.getProductId()).get());
                Set<CartLine> existingLines = new HashSet<>(shoppingCart.getCartLines());
                existingLines.add(cartLine);
                shoppingCart.setCartLines(existingLines);
                shoppingCartRepository.save(shoppingCart);

            }
        }else {
            ShoppingCart shoppingCart =  new ShoppingCart();
            CartLine cartLine = new CartLine();
            cartLine.setProduct(productRepository.findById(requestModel.getProductId()).get());
            cartLine.setQuantity(requestModel.getQuantity());
            Set<CartLine> cartLines =  new HashSet<>();
            cartLines.add(cartLine);
            shoppingCart.setCartLines(cartLines);
            shoppingCart.setStatus(false);
            shoppingCart.setAccountId(requestModel.getAccountId());
            shoppingCartRepository.save(shoppingCart);
            //shoppingCart.setCreatedDate(LocalDate.now());
        }
    }

    @Override
    public void deleteCart() {

    }


    public boolean checkCartExistForUser(Integer id){
        return shoppingCartRepository.findShoppingCartByAccountIdEquals(id) != null;
    }


}
