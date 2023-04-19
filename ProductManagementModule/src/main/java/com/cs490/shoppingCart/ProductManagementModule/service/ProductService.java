package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.ListProductResponseSpecificID;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public interface ProductService {

    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) throws ItemNotFoundException;

    public List<ProductResponse> allProducts(String name, Long categoryId) throws ItemNotFoundException;

    public ProductResponse getProductById(Long id) throws ItemNotFoundException;

    public Boolean deleteProductById(Long id);

    public ProductResponse updateProduct(Product product, Long productId) throws ItemNotFoundException, IdNotMatchException;

    public List<Product> verifiedProducts();

    public List<Product> unverifiedProducts();


    public boolean approveProducts(Long productId);

    public String uploadFile(MultipartFile file);


    public byte[] downloadFile(String fileName);


    public String deleteFile(String fileName);

    public List<ListProductResponseSpecificID> getAllProductWithSpecificIDList(@RequestParam List<Long> productId);

}
