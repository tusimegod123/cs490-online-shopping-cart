package com.cs490.shoppingCart.OrderProcessingModule.service;


import com.cs490.shoppingCart.OrderProcessingModule.model.Product;

public interface ProductService {
    void addProduct(Product product);

    Product getProduct(int id);

     boolean produtExist(int id);
}
