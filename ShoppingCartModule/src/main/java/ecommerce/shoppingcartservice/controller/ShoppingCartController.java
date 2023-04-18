package ecommerce.shoppingcartservice.controller;

import ecommerce.shoppingcartservice.model.dto.RequestModel;
import ecommerce.shoppingcartservice.model.dto.ShoppingCartDTO;
import ecommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartDTO> getCartItems(@PathVariable Long userId){
        boolean validUser;  //rest template call about existance of user;
        if(shoppingCartService.checkCartExistForUser(userId)){
            ShoppingCartDTO cart = shoppingCartService.getCartItems(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<ShoppingCartDTO> addToCart(@RequestBody RequestModel requestModel){
        ShoppingCartDTO shoppingCart = shoppingCartService.addToCart(requestModel);
        return new ResponseEntity<>(shoppingCart,HttpStatus.CREATED);
    }
    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<ShoppingCartDTO> checkOut(@PathVariable Long cartId){
        if(shoppingCartService.checkCartExistance(cartId)){
            ShoppingCartDTO shoppingCart = shoppingCartService.checkOut(cartId);
            return new ResponseEntity<>(shoppingCart,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }







}
