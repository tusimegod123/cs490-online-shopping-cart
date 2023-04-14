package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.mapper.ProductMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse createProduct(ProductRequest productRequest) throws ItemNotFoundException {
        Product product = productMapper.fromCreateProductRequestToDomain(productRequest);
        product.setVerified(false);

        // Get id from input
        Long categoryId = productRequest.getCategoryId();

        // Get id category from DB
        Category category = categoryService.getCategoryById(categoryId);

        // Save product into DB
        Product productToAdd = productRepository.save(product);
        ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(productToAdd);
        productResponse.setCategory(category);
        return productResponse;
    }

    public Product modifyProduct(Product product, Long productId){
        Product productToBeModified = productRepository.findById(productId).get();
        return productRepository.save(productToBeModified);
    }
    public List<Product> allProducts(){
        return productRepository.findAll();
    }
    public List<Product> verifiedProducts(){
        List<Product> products = productRepository.findAll();
        for (Product product: allProducts()) {
            if (product.getVerified()== true){
                products.add(product);
            }
        }
        return products;
    }
}
