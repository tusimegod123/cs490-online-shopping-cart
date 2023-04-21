package com.cs490.shoppingCart.ProductManagementModule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import com.amazonaws.services.s3.AmazonS3;
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

import java.util.*;
import java.util.stream.Collectors;

import com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {ProductServiceImp.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImpTest {

    @MockBean
    private AmazonS3 amazonS3;

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImp productServiceImp;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * Method under test: {@link ProductServiceImp#createProduct(ProductRequest)}
     */
    @Test
    void testCreateProduct() throws ItemNotFoundException, RestClientException {
        when(categoryMapper.fromCategoryResponseToCategory((CategoryResponse) any())).thenReturn(new Category());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(123L);
        categoryResponse.setDescription("The characteristics of someone or something");
        categoryResponse.setName("Name");
        when(categoryService.getCategoryById((Long) any())).thenReturn(categoryResponse);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("password");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        when(productRepository.save((Product) any())).thenReturn(new Product());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        Category category = new Category();
        productResponse.setCategory(category);
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user1);
        productResponse.setVerified(true);
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        when(productMapper.fromCreateProductRequestToDomain((ProductRequest) any())).thenReturn(new Product());

        ProductRequest productRequest = new ProductRequest();
        productRequest.setCategoryId(123L);
        productRequest.setDescription("The characteristics of someone or something");
        productRequest.setImageUrl("https://example.org/example");
        productRequest.setItemCost(10.0d);
        productRequest.setPrice(10.0d);
        productRequest.setProductName("Product Name");
        productRequest.setQty(1);
        productRequest.setUserId(123L);
        productRequest.setVerified(true);
        ProductResponse actualCreateProductResult = productServiceImp.createProduct(productRequest);
        assertSame(productResponse, actualCreateProductResult);
        assertEquals(category, actualCreateProductResult.getCategory());
        assertEquals(user1, actualCreateProductResult.getUser());
        verify(categoryMapper).fromCategoryResponseToCategory((CategoryResponse) any());
        verify(categoryService).getCategoryById((Long) any());
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).save((Product) any());
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
        verify(productMapper).fromCreateProductRequestToDomain((ProductRequest) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
//    @Test
//    void testAllProducts() throws ItemNotFoundException {
//        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
//        when(productRepository.findProductByCategoryId((Long) any())).thenReturn(new ArrayList<>());
//        when(productRepository.findProductByProductName((String) any())).thenReturn(new ArrayList<>());
//        when(productRepository.findAll()).thenReturn(new ArrayList<>());
//        assertTrue(productServiceImp.allProducts("Name", 123L).isEmpty());
//        verify(categoryRepository).findAllById((Iterable<Long>) any());
//        verify(productRepository).findProductByCategoryId((Long) any());
//        verify(productRepository).findProductByProductName((String) any());
//        verify(productRepository).findAll();
//    }

    @Test
    public void testGetAllProducts() throws ItemNotFoundException {

         Product product1 = new Product(1L, "Coca Cola", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);
         Product product2 = new Product(2L, "Pepsi", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);

         List<Product> productList = new ArrayList<>();
         productList.add(product1);
         productList.add(product2);

         when(productRepository.findAll()).thenReturn(productList);

         User user = new User();
         user.setUserId(1L);
         user.setEmail("admin@gmail.com");
         user.setUsername("admin");

         when(restTemplate.getForObject("http://user-service:8082/api/v1/1", User.class)).thenReturn(user);

         Category category1 = new Category(1L, "Pet Care", "pet accessories");
         List<Category> categoryList = new ArrayList<>();
         categoryList.add(category1);

         Set<Long> idSet = new HashSet<>();
         idSet.add(1L);

         when(categoryRepository.findAllById(idSet)).thenReturn(categoryList);

         ProductResponse productResponse = new ProductResponse();
         productResponse.setProductId(1L);
         productResponse.setProductName("Coca Cola");
         productResponse.setQty(5);
         productResponse.setPrice(10.0);
         productResponse.setImageUrl("https://coke.jpg");
         productResponse.setItemCost(8.0);
         productResponse.setDescription("Energy Drink");
         productResponse.setVerified(false);
         productResponse.setCategory(category1);
         productResponse.setUser(user);

         when(productMapper.fromCreateProductResponseToDomain(any(Product.class))).thenReturn(productResponse);
         when(productMapper.fromGetAllProductResponseToDomain(productResponse)).thenReturn(productResponse);

        List<ProductResponse> productResponseList = productServiceImp.allProducts(null, null);
        assertThat(productResponseList.get(0).getProductId()).isEqualTo(productResponse.getProductId());
    }

    @Test
    public void testGetAllProductsWithSpecificNameAndCategory() throws ItemNotFoundException {

        Product product1 = new Product(1L, "Coca Cola", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);
        Product product2 = new Product(2L, "Pepsi", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        Category category1 = new Category(1L, "Pet Care", "pet accessories");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category1);

        Set<Long> idSet = new HashSet<>();
        idSet.add(1L);

        List<Product> productListResult = new ArrayList<>();
        productListResult.add(product1);

        when(productRepository.findAll()).thenReturn(productList);
        when(productRepository.findProductByProductName("Coca Cola")).thenReturn(productListResult);
        when(productRepository.findProductByCategoryId(1L)).thenReturn(productListResult);

        User user = new User();
        user.setUserId(1L);
        user.setEmail("admin@gmail.com");
        user.setUsername("admin");

        when(restTemplate.getForObject("http://user-service:8082/api/v1/1", User.class)).thenReturn(user);

        when(categoryRepository.findAllById(idSet)).thenReturn(categoryList);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(1L);
        productResponse.setProductName("Coca Cola");
        productResponse.setQty(5);
        productResponse.setPrice(10.0);
        productResponse.setImageUrl("https://coke.jpg");
        productResponse.setItemCost(8.0);
        productResponse.setDescription("Energy Drink");
        productResponse.setVerified(false);
        productResponse.setCategory(category1);
        productResponse.setUser(user);

        when(productMapper.fromCreateProductResponseToDomain(any(Product.class))).thenReturn(productResponse);
        when(productMapper.fromGetAllProductResponseToDomain(productResponse)).thenReturn(productResponse);

        List<ProductResponse> productResponseList = productServiceImp.allProducts("Coca Cola", 1L);
        assertThat(productResponseList.get(0).getProductId()).isEqualTo(productResponse.getProductId());
    }


    /**
     * Method under test: {@link ProductServiceImp#getProductById(Long)}
     */
    @Test
    void testGetProductById() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findById((Long) any())).thenReturn(Optional.of(new Category()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("password");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("password");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        Category category = new Category();
        productResponse.setCategory(category);
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user1);
        productResponse.setVerified(true);
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        ProductResponse actualProductById = productServiceImp.getProductById(123L);
        assertSame(productResponse, actualProductById);
        assertEquals(category, actualProductById.getCategory());
        assertEquals(user1, actualProductById.getUser());
        verify(categoryRepository).findById((Long) any());
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).findById((Long) any());
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
    }

    @Test
    public void getProductByIdWithItemNotFound() throws ItemNotFoundException {

        Long id = 1L;

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productServiceImp.getProductById(1L);
        } catch (ItemNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No product found with id: " + id);
        }

    }

    @Test
    public void getProductByIdWithEmptyCategory() throws ItemNotFoundException {

        Long id = 1L;
        Product product = new Product();
        product.setProductId(id);

        Category category = new Category();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(productMapper.fromCreateProductResponseToDomain(product)).thenReturn(productResponse);

        ProductResponse productResult = productServiceImp.getProductById(id);

        assertThat(productResult.getCategory()).isEqualTo(null);

    }

    /**
     * Method under test: {@link ProductServiceImp#verifiedProducts()}
     */
    @Test
    void testVerifiedProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findAllByVerified(anyBoolean())).thenReturn(productList);
        List<Product> actualVerifiedProductsResult = productServiceImp.verifiedProducts();
        assertSame(productList, actualVerifiedProductsResult);
        assertTrue(actualVerifiedProductsResult.isEmpty());
        verify(productRepository).findAllByVerified(anyBoolean());
    }


    /**
     * Method under test: {@link ProductServiceImp#unverifiedProducts()}
     */
    @Test
    void testUnverifiedProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findAllByVerified(anyBoolean())).thenReturn(productList);
        List<Product> actualUnverifiedProductsResult = productServiceImp.unverifiedProducts();
        assertSame(productList, actualUnverifiedProductsResult);
        assertTrue(actualUnverifiedProductsResult.isEmpty());
        verify(productRepository).findAllByVerified(anyBoolean());
    }


    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    public void testApproveSingleProducts() {

        Long id = 1L;
        Product product = new Product();
        product.setProductId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productServiceImp.approveProducts(id);
        assertThat(product.getVerified()).isTrue();
        verify(productRepository).save(product);
    }

    @Test
    public void testApproveAllUnapprovedProducts() {

        Product product1 = new Product(1L, "Coca Cola", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);
        Product product2 = new Product(2L, "Pepsi", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);
        Product product3 = new Product(3L, "Fanta", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        when(productRepository.findAllByVerified(false)).thenReturn(productList);

        productServiceImp.approveProducts(null);

        verify(productRepository, times(3)).save(any(Product.class));
    }

    @Test
    public void testApproveProductsWithAllUnapprovedProducts() {



        Long id = 1L;


    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    public void testGetAllProductWithSpecificIdList() throws ItemNotFoundException {


        Product product1 = new Product(1L, "Coca Cola", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);
        Product product2 = new Product(2L, "Pepsi", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);
        Product product3 = new Product(3L, "Fanta", 10.0, 5, 8.0, "Energy Drink", "https://coke.jpg", false, 1L, 1L);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        Set<Long> productIdSet = new HashSet<>();
        productIdSet.add(1L);

        List<ListProductResponseSpecificID> expectedList = new ArrayList<>();
        ListProductResponseSpecificID listProductResponseSpecificID1 = new ListProductResponseSpecificID(2L, 25.0, 20.0);
        ListProductResponseSpecificID listProductResponseSpecificID2 = new ListProductResponseSpecificID(4L, 25.0, 40.0);
        expectedList.add(listProductResponseSpecificID1);
        expectedList.add(listProductResponseSpecificID2);

        when(productRepository.findAll()).thenReturn(productList);

        ListProductResponseSpecificID listProductResponseSpecificID = new ListProductResponseSpecificID(1L, 10.0, 8.0);
        when(productMapper.fromDomainToListProductResponseSpecificID(product1)).thenReturn(listProductResponseSpecificID);

        productServiceImp.getAllProductWithSpecificIDList(productIdSet);
        verify(productMapper, times(1)).fromDomainToListProductResponseSpecificID(product1);
    }

    @Test
    public void testDeleteProductById() {

        Long id = 1L;
        Product product = new Product();
        product.setProductId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productServiceImp.deleteProductById(id);
    }

    @Test
    public void testDeleteProductByIdEmptyResult() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Boolean isDelete = productServiceImp.deleteProductById(1L);
        assertThat(isDelete).isFalse();
    }

    @Test
    public void testDeleteProductByIdWithEmptyResultDataAccessException() {

        Long id = 1L;

        // mock the behavior of the repository
        when(productRepository.findById(id)).thenReturn(Optional.of(new Product()));
        doThrow(new EmptyResultDataAccessException(1)).when(productRepository).deleteById(id);

        // test the method
        boolean result = productServiceImp.deleteProductById(id);
        assertThat(result).isFalse();

        // verify that the repository methods were called
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateProductById() throws ItemNotFoundException, IdNotMatchException {

        Long id = 1L;
        Product product = new Product();
        product.setProductId(id);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.fromCreateProductResponseToDomain(product)).thenReturn(productResponse);

        productServiceImp.updateProduct(product, id);

        verify(productRepository).save(product);
    }

    @Test
    public void testUpdateProductByIdNotFound() {

        Long id = 1L;
        Product product = new Product();

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            productServiceImp.updateProduct(product, id);
        } catch (IdNotMatchException e) {
            throw new RuntimeException(e);
        } catch (ItemNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Not found for id: " + id);
        }
    }

    @Test
    public void testUpdateProductByIdNotMatch() {

        Long id = 1L;
        Long idNotMatch = 2L;
        Product productNotMatch = new Product(idNotMatch, "Pepsi", 10.0, 4, 8.0, "The energy drink", "https://coke.img", false, 1L, 1L);

        when(productRepository.findById(id)).thenReturn(Optional.of(productNotMatch));

        try {
            productServiceImp.updateProduct(productNotMatch, id);
        } catch (IdNotMatchException e) {
            assertThat(e.getMessage()).isEqualTo("Not match id from url with input id");
        } catch (ItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

