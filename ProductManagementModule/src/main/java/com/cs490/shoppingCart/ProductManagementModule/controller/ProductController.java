package com.cs490.shoppingCart.ProductManagementModule.controller;

import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductRequest productRequest) throws ItemNotFoundException {

        ProductResponse productResponse  = productService.createProduct(productRequest);
        return new ResponseEntity<>("Product has been added, pending approval.", HttpStatus.OK);
    }


    @PutMapping("/approve")
    public ResponseEntity<?> approveProducts(
            @RequestParam(value="productId", required = false) Long productId) {
            boolean approved = productService.approveProducts(productId);
            if(approved){
                return new ResponseEntity<>("Products approved.", HttpStatus.OK);
            }
            return new ResponseEntity<>("Products could not be approved.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/unverified")
    public List<Product> unverifiedProductList(){
        return productService.unverifiedProducts();
    }

    @GetMapping("/verified")
    public List<Product> verifiedProductList(){
        return productService.verifiedProducts();
    }


    @GetMapping()
    public List<Product> allProducts(){
        return productService.allProducts();
    }

}
