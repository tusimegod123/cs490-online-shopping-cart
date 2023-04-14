package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.CreateProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.mapper.ProductMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Product createProduct(CreateProductRequest createProductRequest){
        Product product = productMapper.fromCreateProductRequestToDomain(createProductRequest);
        product.setVerified(false);
        return productRepository.save(product);
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
