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
import java.util.Optional;

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
        Category category = null;
        product.setVerified(false);

        if(productRequest.getCategoryName() == null || productRequest.getCategoryName().isEmpty()){
            // Get id from input
            Long categoryId = productRequest.getCategoryId();

            // Get id category from DB
            category = categoryService.getCategoryById(categoryId);
        }else{
            //A new category introduced
            category = new Category();
            category.setName(productRequest.getCategoryName());
            category.setDescription(productRequest.getCategoryDescription());

        }
        product.setCategory(category);


        // Save product into DB
        Product productToAdd = productRepository.save(product);
        ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(productToAdd);
        productResponse.setCategory(category);
        return productResponse;
    }

    public Product modifyProduct(Product product, Long productId){
        //Can only update productName, description, price, quantity, verified

        Optional<Product> productToBeModified = productRepository.findById(productId);
        if(productToBeModified.isPresent()){
            product.setCategory(productToBeModified.get().getCategory());
            return productRepository.save(product);
        }
        return null;
    }

    public List<Product> allProducts(){
        return productRepository.findAll();
    }
    public List<Product> verifiedProducts(){
        List<Product> products = productRepository.findAllByVerified(true);
        return products;
    }

    public List<Product> unverifiedProducts(){
        List<Product> products = productRepository.findAllByVerified(false);
        return products;
    }

    public Product getProductById(Long id) throws ItemNotFoundException {

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ItemNotFoundException("No product found with id: " +id);
        }
    }

    public boolean approveProducts(Long productId) {
        if(productId != null) {
            //approve single product
            Product product = productRepository.findById(productId).get();
            product.setVerified(true);
            productRepository.save(product);
            return true;
        }else{
            //approve all unapproved products
            List<Product> products = productRepository.findAllByVerified(false);
            for (Product product: products) {
                productRepository.save(product);
            }
            return true;
        }
    }
}
