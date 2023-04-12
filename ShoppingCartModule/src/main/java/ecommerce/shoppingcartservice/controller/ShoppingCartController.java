package ecommerce.shoppingcartservice.controller;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.RequestModel;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import ecommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCart> getCartItems(@PathVariable int userId){
        boolean validUser;  //rest template call about existance of user;
        if(shoppingCartService.checkCartExistForUser(userId)){
            ShoppingCart cart = shoppingCartService.getCartItems(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> addToCart(@RequestBody RequestModel requestModel){
        ShoppingCart shoppingCart = shoppingCartService.addToCart(requestModel);
        return new ResponseEntity<>(shoppingCart,HttpStatus.CREATED);
    }
    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<?> checkOut(@PathVariable int cartId
            //, @RequestBody ShoppingCart shoppingCart
                                      ){
        //if(shoppingCartService.checkCartExistForUser(cartId)){
            shoppingCartService.checkOut(cartId);
            return new ResponseEntity<>(HttpStatus.OK);
        //}
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }







}
