package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.ListProductResponseSpecificID;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

public interface ProductService {

     ProductResponse createProduct(@RequestBody ProductRequest productRequest) throws ItemNotFoundException;
     List<ProductResponse> allProducts(String name, Long categoryId, Long userId) throws ItemNotFoundException;
     ProductResponse getProductById(Long id) throws ItemNotFoundException;
     Boolean deleteProductById(Long id);
     ProductResponse updateProduct(Product product, Long productId) throws ItemNotFoundException, IdNotMatchException;
     List<Product> verifiedProducts();
     List<Product> unverifiedProducts() throws ItemNotFoundException;
     void approveProducts(Long productId) throws ItemNotFoundException;
     List<ListProductResponseSpecificID> getAllProductWithSpecificIDList(@RequestParam Set<Long> productId)throws ItemNotFoundException;
}
