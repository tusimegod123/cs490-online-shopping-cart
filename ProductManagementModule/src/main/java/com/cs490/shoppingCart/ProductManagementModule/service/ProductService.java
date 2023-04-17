package com.cs490.shoppingCart.ProductManagementModule.service;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.IdNotMatchException;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.mapper.CategoryMapper;
import com.cs490.shoppingCart.ProductManagementModule.mapper.ProductMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.model.User;
import com.cs490.shoppingCart.ProductManagementModule.repository.CategoryRepository;
import com.cs490.shoppingCart.ProductManagementModule.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestTemplate restTemplate;

//    @Value("${user.endpoint}")
//    private String userEndpoint;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse createProduct(ProductRequest productRequest) throws ItemNotFoundException {
        Product product = productMapper.fromCreateProductRequestToDomain(productRequest);
        product.setVerified(false);

//        //Get User
         User user = restTemplate.getForObject("http://localhost:9898/api/v1/users/{id}",
         User.class, productRequest.getUserId());

        // Get id from input
        Long categoryId = productRequest.getCategoryId();

        // Get id category from DB
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        Category category = categoryMapper.fromCategoryResponseToCategory(categoryResponse);

        // Save product into DB
        Product productToAdd = productRepository.save(product);
        ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(productToAdd);
        productResponse.setCategory(category);
//        productResponse.setUser(user);
        return productResponse;
    }

    public Product modifyProduct(Product product, Long productId){
        Product productToBeModified = productRepository.findById(productId).get();
        return productRepository.save(productToBeModified);
    }
    public List<ProductResponse> allProducts() throws ItemNotFoundException {

        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        Set<Long> categoryIds = products.stream().map(p -> p.getCategoryId()).collect(Collectors.toSet());

        HashMap<Long, Category> categoryHashMap = getCategoryMap(categoryIds);

        for (Product product : products) {
            Category category = categoryHashMap.get(product.getCategoryId());
            ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(product);
            productResponse.setCategory(category);
            productResponses.add(productMapper.fromGetAllProductResponseToDomain(productResponse));
        }
        return productResponses;
    }

    private HashMap<Long, Category> getCategoryMap(Set<Long> categoryIds) {

        List<Category> categoryList = categoryRepository.findAllById(categoryIds);
        HashMap<Long, Category> categoryMap = new HashMap<>();

        for (Category category : categoryList) {
            categoryMap.put(category.getCategoryId(), category);
        }

        return categoryMap;
    }

    public ProductResponse getProductById(Long id) throws ItemNotFoundException {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {

            Product productResult = product.get();

            Long categoryId = productResult.getCategoryId();
            Category category = categoryRepository.findById(categoryId).orElseThrow(()-> {
                return new ItemNotFoundException("No category found");
            });

            ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(productResult);
            productResponse.setCategory(category);
            return productResponse;
        } else {
            throw new ItemNotFoundException("No product found with id: " +id);
        }
    }

    public Boolean deleteProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            return false;
        }

        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
    }

    public Product updateProduct(Product product, Long productId) throws ItemNotFoundException, IdNotMatchException {

        Optional<Product> productToBeModified = productRepository.findById(productId);

        if (productToBeModified.isEmpty()) {
            throw new ItemNotFoundException("Not found for id: " + productId );
        }

        if (productId != product.getProductId()) {
            throw new IdNotMatchException("Not match id from url with input id");
        }

        return productRepository.save(product);
    }


    //Product Approval

    public List<Product> verifiedProducts(){
        List<Product> products = productRepository.findAllByVerified(true);
        return products;
    }

    public List<Product> unverifiedProducts(){
        List<Product> products = productRepository.findAllByVerified(false);
        return products;
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
