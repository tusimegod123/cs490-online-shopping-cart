package ecommerce.shoppingcartservice.service;

import ecommerce.shoppingcartservice.model.Product;

public interface ProductService {
    void addProduct(Product product);

    Product getProduct(int id);

     boolean produtExist(int id);
}
