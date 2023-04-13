package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product){
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
