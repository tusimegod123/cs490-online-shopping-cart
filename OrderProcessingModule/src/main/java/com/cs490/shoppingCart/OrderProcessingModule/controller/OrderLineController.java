//package com.cs490.shoppingCart.OrderProcessingModule.controller;
//
//import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
//import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;
//import com.cs490.shoppingCart.OrderProcessingModule.service.OrderLineService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/cartLine")
//public class OrderLineController {
//
//    @Autowired
//    OrderLineService orderLineService;
//
//
//    @DeleteMapping("/{orderLineId}")
//    public ResponseEntity<?> removeCartLine(@PathVariable Integer cartLineId){
//        if(orderLineService.checkCartLineExistence(cartLineId)){
//            orderLineService.removeCartLine(cartLineId);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PutMapping()
//    public ResponseEntity<OrderLine> updateCartLine(@RequestBody OrderLine orderLine){
//        if(orderLineService.checkCartLineExistence(orderLine.getId())){
//            OrderLine cartLine1 = orderLineService.updateCartLine(orderLine);
//            return new ResponseEntity<>(cartLine1,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//    @PostMapping()
//    public Order createOrderForGuest(){
//
//    }
//
//
//}
