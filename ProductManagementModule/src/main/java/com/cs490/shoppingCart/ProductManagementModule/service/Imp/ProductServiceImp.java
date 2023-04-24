package com.cs490.shoppingCart.ProductManagementModule.service.Imp;

import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.dto.ListProductResponseSpecificID;
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
import com.cs490.shoppingCart.ProductManagementModule.service.CategoryService;
import com.cs490.shoppingCart.ProductManagementModule.service.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImp implements ProductService {


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

    @Value("${userServiceUrl}")
    private String userEndpoint;

//    @Value("${application.bucket.name}")
//    private String bucketName;

//    @Autowired
//    private AmazonS3 s3Client;

    public ProductServiceImp(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * To crate a product
     * @param productRequest product request for creating a product
     * @return Product Response
     * @throws ItemNotFoundException
     */
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) throws ItemNotFoundException {
        Product product = productMapper.fromCreateProductRequestToDomain(productRequest);
        product.setVerified(false);

        //Get User object from userService
        User user = restTemplate.getForObject(userEndpoint + "/users/{id}",
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
        productResponse.setUser(user);
        return productResponse;
    }

    /**
     * Get all the products
     * @param name search product by product name
     * @param categoryId search product by category Id
     * @return List of Product Response
     * @throws ItemNotFoundException
     */
    public List<ProductResponse> allProducts(String name, Long categoryId, Long userId) throws ItemNotFoundException {

        List<Product> products = productRepository.findAll();

        //Search Product by ProductName
        if(name!=null){
            for(Product p : products){
                if(name.equalsIgnoreCase(p.getProductName())){
                    products = productRepository.findProductByProductName(name);
                    break;
                }
                else {
                    throw new ItemNotFoundException("Product Name you are searching is not found.");
                }
            }

        }

        //Search Product by categoryId
        if(categoryId!=null){
                for(Product p: products){
                    if(categoryId == p.getCategoryId()){
                        products = productRepository.findProductByCategoryId(categoryId);
                        break;
                    }else {
                        throw new ItemNotFoundException("Category ID you are searching is not found.");
                    }
                }
        }

        //Search Product by userId
        if(userId!=null){
//            for(Product p: products){
//                if(userId == p.getUserId()){
                    products = productRepository.findProductByUserId(userId);
//                    break;
//                }else {
//                    throw new ItemNotFoundException("User ID you are searching is not found.");
//                }
            }
//        }

        if(products.size()==0){
            throw new ItemNotFoundException("Products list is empty");
        }

        List<ProductResponse> productResponses = new ArrayList<>();
        Set<Long> categoryIds = products.stream().map(p -> p.getCategoryId()).collect(Collectors.toSet());

        HashMap<Long, Category> categoryHashMap = getCategoryMap(categoryIds);

        for (Product product : products) {
            User user = restTemplate.getForObject(userEndpoint + "/users/{id}",
                    User.class, product.getUserId());

            Category category = categoryHashMap.get(product.getCategoryId());
            ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(product);
            productResponse.setCategory(category);
            productResponse.setUser(user);
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

    /**
     * Get Product by ProductID
     * @param id product Id
     * @return ProductResponse
     * @throws ItemNotFoundException
     */
    public ProductResponse getProductById(Long id) throws ItemNotFoundException {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {

            Product productResult = product.get();

            Long categoryId = productResult.getCategoryId();
            Category category;
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);

            if (categoryOpt.isPresent()) {
                category = categoryOpt.get();
            } else {
                category = null;
            }
            User user = restTemplate.getForObject(userEndpoint + "/users/{id}",
                    User.class, productResult.getUserId());

            ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(productResult);
            productResponse.setCategory(category);
            productResponse.setUser(user);
            return productResponse;
        } else {
            throw new ItemNotFoundException("No product found with id: " +id);
        }
    }

    /**
     * Delete the product
     * @param id : product Id
     * @return boolean true or false
     */
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

    /**
     * To Update the product
     * @param product request body
     * @param productId product Id
     * @return ProductResponse
     * @throws ItemNotFoundException
     * @throws IdNotMatchException
     */
    public ProductResponse updateProduct(Product product, Long productId) throws ItemNotFoundException, IdNotMatchException {

        Optional<Product> productToBeModified = productRepository.findById(productId);

        if (productToBeModified.isEmpty()) {
            throw new ItemNotFoundException("Not found for id: " + productId );
        }

        if (productId != product.getProductId()) {
            throw new IdNotMatchException("Not match id from url with input id");
        }

        User user = restTemplate.getForObject(userEndpoint + "/users/{id}",
                User.class, product.getUserId());

        Product productResult = productRepository.save(product);
        productResult.setUserId(user.getUserId());
        ProductResponse productResponse = productMapper.fromCreateProductResponseToDomain(productResult);
        productResponse.setUser(user);

        return productResponse;
    }


    /**
     * To get all approval products
     * @return list of product
     */
    public List<Product> verifiedProducts() {
        List<Product> products = productRepository.findAllByVerified(true);
        return products;
    }

    /**
     * To get all un approval products
     * @return list of product
     */
    public List<Product> unverifiedProducts() throws ItemNotFoundException {
        List<Product> products = productRepository.findAllByVerified(false);
        if(products.size()==0){
            throw new ItemNotFoundException("All the products are approved already.");
        }
        return products;
    }

    /**
     * To approve the product one by one
     * @param productId : product Id
     * @return true or false boolean
     */
    public void approveProducts(Long productId) throws ItemNotFoundException {

        if (productId == null) {  //approve all unapproved products
            List<Product> products = productRepository.findAllByVerified(false);
            for (Product product: products) {
                product.setVerified(true);
                productRepository.save(product);
            }
        } else {   //approve single product
            try{
                Product product = productRepository.findById(productId).get();
                product.setVerified(true);
                productRepository.save(product);
            } catch (Exception e){
                throw new ItemNotFoundException("Product ID to approve is not found!");
            }

        }
    }
    /**
     * This is optional code
     * Upload image when create a product
     */


//    public String uploadFile(MultipartFile file) {
//        File fileObj = convertMultiPartFileToFile(file);
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
//        fileObj.delete();
//        return "File uploaded : " + fileName;
//    }
//
//
//    public byte[] downloadFile(String fileName) {
//        S3Object s3Object = s3Client.getObject(bucketName, fileName);
//        S3ObjectInputStream inputStream = s3Object.getObjectContent();
//        try {
//            byte[] content = IOUtils.toByteArray(inputStream);
//            return content;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public String deleteFile(String fileName) {
//        s3Client.deleteObject(bucketName, fileName);
//        return fileName + " removed ...";
//    }
//
//
//    private File convertMultiPartFileToFile(MultipartFile file) {
//        File convertedFile = new File(file.getOriginalFilename());
//        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
//            fos.write(file.getBytes());
//        } catch (IOException e) {
//            log.error("Error converting multipartFile to file", e);
//        }
//        return convertedFile;
//    }

    @Override
    public List<ListProductResponseSpecificID> getAllProductWithSpecificIDList(Set<Long> productIdSet) throws ItemNotFoundException{

        List<Product> products = productRepository.findAll();
        List<ListProductResponseSpecificID> list = new ArrayList<>();

        boolean check = false;
        for (Product product : products) {
            Long productId = product.getProductId();
            if (productIdSet.contains(productId)) {
                list.add(productMapper.fromDomainToListProductResponseSpecificID(product));
                check = true;
            }
        }

        if(check == false){
            throw new ItemNotFoundException("Product ID you input is not found, Check it again!");
        }

        return list;
    }
}
