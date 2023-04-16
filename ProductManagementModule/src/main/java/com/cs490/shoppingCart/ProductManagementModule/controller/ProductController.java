package com.cs490.shoppingCart.ProductManagementModule.controller;

import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.service.ProductService;
import jakarta.validation.Valid;
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
    public ProductResponse saveProduct(@RequestBody @Valid ProductRequest productRequest) throws ItemNotFoundException {

        ProductResponse productResponse  = productService.createProduct(productRequest);

        return productResponse;
    }
//    @GetMapping("/verified")
//    public List<Product> productList(){
//
//        return productService.allProducts();
//    }

    @GetMapping
    public List<ProductResponse> getAllProduct() throws ItemNotFoundException {

        List<ProductResponse> products= productService.allProducts();

        return products;
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) throws ItemNotFoundException {

        ProductResponse productResponse  = productService.getProductById(id);
        return productResponse;
    }


}
