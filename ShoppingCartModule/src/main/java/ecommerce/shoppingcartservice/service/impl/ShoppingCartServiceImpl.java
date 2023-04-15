package ecommerce.shoppingcartservice.service.impl;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.Product;
import ecommerce.shoppingcartservice.model.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import ecommerce.shoppingcartservice.repository.CartLineRepository;
import ecommerce.shoppingcartservice.repository.ShoppingCartRepository;
import ecommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartLineRepository cartLineRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ShoppingCart getCartItems(int id) {
        return shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals(id,false);
    }

    //update
    @Override
    public ShoppingCart addToCart(RequestModel requestModel) {

        if(checkCartExistForUserAndStatusFalse(requestModel.getUserId())){
            ShoppingCart shoppingCart = getCartItems(requestModel.getUserId());

            Optional<CartLine> cartLineEx = shoppingCart.getCartLines().stream().filter(cartLine -> cartLine.getProductId().compareTo(
                    //requestModel.getProductId()) && shoppingCart.getCartStatus() == false ).findAny();
                     requestModel.getProduct().getId()) == 0 ).findAny();
            if(cartLineEx.isPresent()){
                CartLine cartLineExisting = cartLineEx.get();
                cartLineExisting.setQuantity(cartLineExisting.getQuantity()+ requestModel.getQuantity());
                cartLineExisting.setPrice(requestModel.getProduct().getPrice() * cartLineExisting.getQuantity());
                Set<CartLine> cartLineSet = new HashSet<>(shoppingCart.getCartLines());
                cartLineSet.add(cartLineExisting);
                shoppingCart.setCartLines(cartLineSet);
                shoppingCart.setTotalPrice(cartLineSet.stream().map(cartLine1 -> cartLine1.getPrice()).reduce(0.0,Double::sum));
                return shoppingCartRepository.save(shoppingCart);

            }else{

                CartLine cartLine = new CartLine();
                cartLine.setQuantity(requestModel.getQuantity());
                cartLine.setProductId(requestModel.getProduct().getId());
                cartLine.setPrice(requestModel.getProduct().getPrice() * requestModel.getQuantity());
                        //productRepository.findById(requestModel.getProduct());
                Set<CartLine> existingLines = new HashSet<>(shoppingCart.getCartLines());
                existingLines.add(cartLine);
                shoppingCart.setCartLines(existingLines);
                shoppingCart.setTotalPrice(existingLines.stream().map(cartLine1 -> cartLine1.getPrice()).reduce(0.0,Double::sum));
                return shoppingCartRepository.save(shoppingCart);


            }
        }else {
            ShoppingCart shoppingCart =  new ShoppingCart();
            CartLine cartLine = new CartLine();
            cartLine.setProductId(requestModel.getProduct().getId());
            cartLine.setQuantity(requestModel.getQuantity());
            cartLine.setPrice(requestModel.getProduct().getPrice() * requestModel.getQuantity());
            Product product = new Product();
            cartLine.setPrice(requestModel.getQuantity() * requestModel.getProduct().getPrice());
            Set<CartLine> cartLines =  new HashSet<>();
            cartLines.add(cartLine);
            shoppingCart.setCartLines(cartLines);
            shoppingCart.setCartStatus(false);
            shoppingCart.setCartDate(LocalDateTime.now());
            shoppingCart.setUserId(requestModel.getUserId());
            shoppingCart.setTotalPrice(cartLines.stream().map(cartLine1 -> cartLine1.getPrice()).reduce(0.0,Double::sum));
            return shoppingCartRepository.save(shoppingCart);
            //shoppingCart.setCreatedDate(LocalDate.now());
        }
    }

    @Override
    public void deleteCart() {
        // if necessary going to be implemented
    }


    public boolean checkCartExistForUser(Integer id){
        return shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals(id,false) != null;
    }

    public boolean checkCartExistForUserAndStatusFalse(Integer id){
        return shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals(id,false) != null;
    }

    @Override
    public ShoppingCart checkOut(int cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();
        shoppingCart.setCartStatus(true);
        return shoppingCartRepository.save(shoppingCart);
       // restTemplate.postForLocation("/url to be updated here ",shoppingCart);
    }

    @Override
    public boolean checkCartExistance(int cartId) {
        if(shoppingCartRepository.existsById(cartId)){
            boolean cartExist = shoppingCartRepository.findById(cartId).get().getCartStatus();
            return !cartExist;
        }
        return false;
    }


}
