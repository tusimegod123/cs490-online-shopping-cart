package com.cs490.shoppingCart.ProductManagementModule.controller;
import com.cs490.shoppingCart.ProductManagementModule.dto.ListProductResponseSpecificID;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * This Class represents Product Controller for Product Management Module
 * @version : version 1.0.0
 * @author sophearyrin , sonytanget
 * @since: 2023
 */
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * To create a product
     * @param productRequest product Request as a request boby
     * @return : Product Response
     * @throws ItemNotFoundException
     */
    @PostMapping

    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductRequest productRequest) {

        ProductResponse productResponse;
        try {
            productResponse  = productService.createProduct(productRequest);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            if (e.getMessage() == errorMessage) {
                return new ResponseEntity<>(" Sorry ID not found, please check your userID or CategoryID again!" ,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * Get all the products
     * @param name search product by product name
     * @param categoryId search product by category id
     * @return list of products response
     * @throws ItemNotFoundException
     */
    @GetMapping
    public ResponseEntity<Object> getAllProduct(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) Long categoryId,
                                                @RequestParam(required = false) Long userId) throws ItemNotFoundException {

        List<ProductResponse> products;
        try {
            products = productService.allProducts(name, categoryId, userId);
            return new ResponseEntity<>(products,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get a product by product Id
     * @param id product id
     * @return Object
     * @throws ItemNotFoundException
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) throws ItemNotFoundException {

        try {
            ProductResponse productResponse  = productService.getProductById(id);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete the product by product id
     * @param id product id
     * @return Object
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable Long id) {

        boolean isDelete = productService.deleteProductById(id);

        if (isDelete) {
            return new ResponseEntity<>("Product with id: " + id + " is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product with id: " + id + " cannot be deleted", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update the product by product Id
     * @param id product id
     * @param product Product as a request body
     * @return Object
     * @throws IdNotMatchException
     * @throws ItemNotFoundException
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable Long id, @RequestBody Product product) throws IdNotMatchException, ItemNotFoundException {

        ProductResponse productResponse;

        try {
            productResponse = productService.updateProduct(product,id);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IdNotMatchException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new  ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * Approve the product by product id
     * @param productId product Id
     * @return Object
     */

    @PutMapping("/approve")
    public ResponseEntity<?> approveProducts(
            @RequestParam(value="productId", required = false) Long productId)
            throws ItemNotFoundException{

        try{
            productService.approveProducts(productId);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Products approved.", HttpStatus.OK);
    }


    @GetMapping("/unverified")
    public ResponseEntity<?> unverifiedProductList() throws ItemNotFoundException {
        List<Product> products = new ArrayList<>();
        try {
            products = productService.unverifiedProducts();
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/verified")
    public List<Product> verifiedProductList(){
        return productService.verifiedProducts();
    }

    @GetMapping("/productDetail")
    public ResponseEntity<?> getAllProductWithSpecificIDList(@RequestParam(required = true) Set<Long> productId) throws ItemNotFoundException {
        List<ListProductResponseSpecificID> productResponseSpecificIDS = new ArrayList<>();
        try{
            productResponseSpecificIDS = productService.getAllProductWithSpecificIDList(productId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(productResponseSpecificIDS, HttpStatus.OK);
    }
}
