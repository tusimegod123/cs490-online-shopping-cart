package ecommerce.shoppingcartservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerce.shoppingcartservice.model.dto.ShoppingCartDTO;
import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.dto.ProductDTO;
import ecommerce.shoppingcartservice.model.dto.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import ecommerce.shoppingcartservice.repository.CartLineRepository;
import ecommerce.shoppingcartservice.repository.ShoppingCartRepository;
import ecommerce.shoppingcartservice.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ShoppingCartDTO getCartItems(Long id) {
        ShoppingCart cart = shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals(id,false);
        ShoppingCartDTO shoppingCartDTO = modelMapper.map(cart, ShoppingCartDTO.class);
        return shoppingCartDTO;
    }

    //update
    @Override
    public ShoppingCartDTO addToCart(RequestModel requestModel) {

        if(checkCartExistForUserAndStatusFalse(requestModel.getUserId())){
            ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(requestModel.getUserId());

            Optional<CartLine> cartLineEx = shoppingCart.getCartLines().stream().filter(cartLine -> cartLine.getProductId().compareTo(
                     requestModel.getProductDTO().getId()) == 0 ).findAny();
            if(cartLineEx.isPresent()){
                CartLine cartLineExisting = cartLineEx.get();
                cartLineExisting.setQuantity(cartLineExisting.getQuantity()+ requestModel.getQuantity());
                cartLineExisting.setPrice(requestModel.getProductDTO().getPrice() * cartLineExisting.getQuantity());
                Set<CartLine> cartLineSet = new HashSet<>(shoppingCart.getCartLines());
                cartLineSet.add(cartLineExisting);
                shoppingCart.setCartLines(cartLineSet);
                shoppingCart.setTotalPrice(cartLineSet.stream().map(cartLine1 -> cartLine1.getPrice()).reduce(0.0,Double::sum));
                ShoppingCart cart =  shoppingCartRepository.save(shoppingCart);
                return modelMapper.map(cart,ShoppingCartDTO.class);

            }else{

                CartLine cartLine = new CartLine();
                cartLine.setQuantity(requestModel.getQuantity());
                cartLine.setProductId(requestModel.getProductDTO().getId());

                try {
                    ObjectMapper ob = new ObjectMapper();
                    ProductDTO productDTO = requestModel.getProductDTO();
                    cartLine.setProductInfo(ob.writeValueAsString(productDTO));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                cartLine.setPrice(requestModel.getProductDTO().getPrice() * requestModel.getQuantity());
                        //productRepository.findById(requestModel.getProduct());
                Set<CartLine> existingLines = new HashSet<>(shoppingCart.getCartLines());
                existingLines.add(cartLine);
                shoppingCart.setCartLines(existingLines);
                shoppingCart.setTotalPrice(existingLines.stream().map(cartLine1 -> cartLine1.getPrice()).reduce(0.0,Double::sum));
                ShoppingCart cart =shoppingCartRepository.save(shoppingCart);
                return modelMapper.map(cart, ShoppingCartDTO.class);


            }
        }else {
            ShoppingCart shoppingCart =  new ShoppingCart();
            CartLine cartLine = new CartLine();
            cartLine.setProductId(requestModel.getProductDTO().getId());
            cartLine.setQuantity(requestModel.getQuantity());
            cartLine.setPrice(requestModel.getProductDTO().getPrice() * requestModel.getQuantity());
            try {
                ObjectMapper ob = new ObjectMapper();
                ProductDTO productDTO = requestModel.getProductDTO();
                cartLine.setProductInfo(ob.writeValueAsString(productDTO));
            }catch (Exception ex){
                ex.printStackTrace();
            }
            cartLine.setPrice(requestModel.getQuantity() * requestModel.getProductDTO().getPrice());
            Set<CartLine> cartLines =  new HashSet<>();
            cartLines.add(cartLine);
            shoppingCart.setCartLines(cartLines);
            shoppingCart.setCartStatus(false);
            shoppingCart.setCartDate(LocalDateTime.now());
            shoppingCart.setUserId(requestModel.getUserId());
            shoppingCart.setTotalPrice(cartLines.stream().map(cartLine1 -> cartLine1.getPrice()).reduce(0.0,Double::sum));
            ShoppingCart cart = shoppingCartRepository.save(shoppingCart);
            return modelMapper.map(cart,ShoppingCartDTO.class);
            //shoppingCart.setCreatedDate(LocalDate.now());
        }
    }

    @Override
    public void deleteCart() {
        // if necessary going to be implemented
    }


    public boolean checkCartExistForUser(Long id){
        return shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals(id,false) != null;
    }

    public boolean checkCartExistForUserAndStatusFalse(Long id){
        return shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals(id,false) != null;
    }

    @Override
    public ShoppingCartDTO checkOut(Long cartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).get();
        shoppingCart.setCartStatus(true);
        return  modelMapper.map(shoppingCartRepository.save(shoppingCart), ShoppingCartDTO.class);
       // restTemplate.postForLocation("/url to be updated here ",shoppingCart);
    }

    @Override
    public boolean checkCartExistance(Long cartId) {
        if(shoppingCartRepository.existsById(cartId)){
            boolean cartExist = shoppingCartRepository.findById(cartId).get().getCartStatus();
            return !cartExist;
        }
        return false;
    }


}
