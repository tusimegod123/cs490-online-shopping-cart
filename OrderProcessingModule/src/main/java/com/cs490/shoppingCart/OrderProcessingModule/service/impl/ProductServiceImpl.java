package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import com.cs490.shoppingCart.OrderProcessingModule.model.Product;
import com.cs490.shoppingCart.OrderProcessingModule.repository.ProductRepository;
import com.cs490.shoppingCart.OrderProcessingModule.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id).get();
    }
    @Override
    public boolean produtExist(int id){
        return productRepository.existsById(id);
    }
}
