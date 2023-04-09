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

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCartItems(@PathVariable int id){
        if(shoppingCartService.checkCartExistForUser(id)){
            ShoppingCart cart = shoppingCartService.getCartItems(id);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> addToCart(@RequestBody RequestModel requestModel){
        shoppingCartService.addToCart(requestModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/{id}/checkout")
    public ResponseEntity<?> checkOut(@PathVariable int id, @RequestBody ShoppingCart shoppingCart){
        if(shoppingCartService.checkCartExistForUser(id)){
            shoppingCartService.checkOut(shoppingCart);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
/*
    @DeleteMapping()
    public ResponseEntity<?> deleteCart(){
        shoppingCartService.deleteCart()
    }

    @PutMapping()
    public ResponseEntity<?> updateCart(@RequestBody RequestModel requestModel){

    }*/






}
