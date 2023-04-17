package ecommerce.shoppingcartservice.service.impl;

import ecommerce.shoppingcartservice.model.Product;
import ecommerce.shoppingcartservice.repository.ProductRepository;
import ecommerce.shoppingcartservice.service.ProductService;
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
