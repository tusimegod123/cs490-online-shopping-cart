package com.cs490.shoppingCart.ProductManagementModule.controller;

import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<Object> getProductById(@PathVariable Long id) throws ItemNotFoundException {

        try {
            ProductResponse productResponse  = productService.getProductById(id);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable Long id) {

        boolean isDelete = productService.deleteProductById(id);

        if (isDelete) {
            return new ResponseEntity<>("Product with id: " + id + " is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product with id: " + id + " cannot be deleted", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable Long id, @RequestBody Product product) throws IdNotMatchException, ItemNotFoundException {

        try {
            productService.updateProduct(product,id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IdNotMatchException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new  ResponseEntity<>("Product with id " + id + " is successfully updated", HttpStatus.OK);
    }

}
