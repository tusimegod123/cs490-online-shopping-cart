package com.cs490.shoppingCart.ProductManagementModule.controller;

import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }
    @GetMapping("/verified")
    public List<Product> productList(){
        return productService.allProducts();
    }
}
