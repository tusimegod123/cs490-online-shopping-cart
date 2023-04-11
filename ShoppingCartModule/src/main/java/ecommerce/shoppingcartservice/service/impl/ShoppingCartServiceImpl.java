package ecommerce.shoppingcartservice.service.impl;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.Product;
import ecommerce.shoppingcartservice.model.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import ecommerce.shoppingcartservice.repository.CartLineRepository;
import ecommerce.shoppingcartservice.repository.ProductRepository;
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
    private ProductRepository productRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ShoppingCart getCartItems(int id) {
        return shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals(id,false);
    }

    //update
    @Override
    public void addToCart(RequestModel requestModel) {

        if(checkCartExistForUserAndStatusFalse(requestModel.getAccountId())){
            ShoppingCart shoppingCart = getCartItems(requestModel.getAccountId());

            Optional<CartLine> cartLineEx = shoppingCart.getCartLines().stream().filter(cartLine -> cartLine.getProduct().getId().equals(
                    //requestModel.getProductId()) && shoppingCart.getCartStatus() == false ).findAny();
                     requestModel.getProduct().getId())).findAny();
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
                cartLine.setProduct(requestModel.getProduct());
                        //productRepository.findById(requestModel.getProduct());
                Set<CartLine> existingLines = new HashSet<>(shoppingCart.getCartLines());
                existingLines.add(cartLine);
                shoppingCart.setCartLines(existingLines);
                shoppingCartRepository.save(shoppingCart);

            }
        }else {
            ShoppingCart shoppingCart =  new ShoppingCart();
            CartLine cartLine = new CartLine();

            Product product = requestModel.getProduct();
                    //productRepository.findById(requestModel.getProduct()).get();
            cartLine.setProduct(product);
            cartLine.setQuantity(requestModel.getQuantity());
            cartLine.setPrice(requestModel.getQuantity() * product.getPrice());
            Set<CartLine> cartLines =  new HashSet<>();
            cartLines.add(cartLine);
            shoppingCart.setCartLines(cartLines);
            shoppingCart.setCartStatus(false);
            shoppingCart.setCartDate(LocalDateTime.now());
            shoppingCart.setUserId(requestModel.getAccountId());
            shoppingCart.setTotalPrice(cartLines.stream().map(cartLine1 -> cartLine1.getPrice()).reduce(0.0,Double::sum));
            shoppingCartRepository.save(shoppingCart);
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
    public void checkOut(int cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();
        shoppingCart.setCartStatus(true);
        shoppingCartRepository.save(shoppingCart);
       // restTemplate.postForLocation("/url to be updated here ",shoppingCart);
    }


}
