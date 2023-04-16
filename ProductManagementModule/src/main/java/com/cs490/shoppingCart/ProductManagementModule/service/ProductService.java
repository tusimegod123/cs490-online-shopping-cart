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

import java.util.ArrayList;
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
    public List<ProductResponse> allProducts() throws ItemNotFoundException {

        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();

        for(int i = 0; i<products.size(); i++){

            // Retrieve category detail from DB
            Product product = products.get(i);
            Long categoryId = product.getCategoryId();
            String categoryName = categoryService.getCategoryById(categoryId).getName();
            String categoryDescription = categoryService.getCategoryById(categoryId).getDescription();

            Category category = new Category();
            category.setCategoryId(categoryId);
            category.setName(categoryName);
            category.setDescription(categoryDescription);

            ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(product);
            productResponse.setCategory(category);
            productResponses.add(productMapper.fromGetAllProductResponseToDomain(productResponse));
        }
        return productResponses;
    }
//    public List<Product> verifiedProducts(){
//        List<Product> products = productRepository.findAll();
//        for (Product product: allProducts()) {
//            if (product.getVerified()== true){
//                products.add(product);
//            }
//        }
//        return products;
//    }

    public ProductResponse getProductById(Long id) throws ItemNotFoundException {

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product productResult = product.get();
            ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(productResult);
            return productResponse;
        } else {
            throw new ItemNotFoundException("No product found with id: " +id);
        }
    }
}
