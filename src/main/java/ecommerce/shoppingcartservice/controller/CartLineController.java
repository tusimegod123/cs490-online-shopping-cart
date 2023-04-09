package ecommerce.shoppingcartservice.controller;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.service.CartLineService;
import ecommerce.shoppingcartservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartLine")
public class CartLineController {

    @Autowired
    CartLineService cartLineService;


    @DeleteMapping("/{cartLineId}")
    public ResponseEntity<?> removeCartLine(@PathVariable Integer cartLineId){
        if(cartLineService.checkCartLineExistence(cartLineId)){
            cartLineService.removeCartLine(cartLineId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public ResponseEntity<?> updateCartLine(@RequestBody CartLine cartLine){
        if(cartLineService.checkCartLineExistence(cartLine.getId())){
            cartLineService.updateCartLine(cartLine);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
