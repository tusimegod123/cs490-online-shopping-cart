package com.cs490.shoppingCart.ProductManagementModule.service.Imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.StringInputStream;
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
import com.cs490.shoppingCart.ProductManagementModule.service.CategoryService;

import java.io.DataInputStream;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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
        user.setPassword("iloveyou");
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
        user1.setPassword("iloveyou");
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
     * Method under test: {@link ProductServiceImp#createProduct(ProductRequest)}
     */
    @Test
    void testCreateProduct2() throws ItemNotFoundException, RestClientException {
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
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productMapper.fromCreateProductResponseToDomain((Product) any()))
                .thenThrow(new ItemNotFoundException("An error occurred"));
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
        assertThrows(ItemNotFoundException.class, () -> productServiceImp.createProduct(productRequest));
        verify(categoryMapper).fromCategoryResponseToCategory((CategoryResponse) any());
        verify(categoryService).getCategoryById((Long) any());
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).save((Product) any());
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
        verify(productMapper).fromCreateProductRequestToDomain((ProductRequest) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#createProduct(ProductRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateProduct3() throws ItemNotFoundException, RestClientException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cs490.shoppingCart.ProductManagementModule.model.Product.setVerified(java.lang.Boolean)" because "product" is null
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.createProduct(ProductServiceImp.java:81)
        //   In order to prevent createProduct(ProductRequest)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   createProduct(ProductRequest).
        //   See https://diff.blue/R013 to resolve this issue.

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
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<Object>) any(), (Object[]) any())).thenReturn("For Object");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        when(productRepository.save((Product) any())).thenReturn(new Product());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
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
        when(productMapper.fromCreateProductRequestToDomain((ProductRequest) any())).thenReturn(null);

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
        productServiceImp.createProduct(productRequest);
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
    @Test
    void testAllProducts() throws ItemNotFoundException {
        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        when(productRepository.findProductByCategoryId((Long) any())).thenReturn(new ArrayList<>());
        when(productRepository.findProductByProductName((String) any())).thenReturn(new ArrayList<>());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(productServiceImp.allProducts("Name", 123L).isEmpty());
        verify(categoryRepository).findAllById((Iterable<Long>) any());
        verify(productRepository).findProductByCategoryId((Long) any());
        verify(productRepository).findProductByProductName((String) any());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
    @Test
    void testAllProducts2() throws ItemNotFoundException {
        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        when(productRepository.findProductByCategoryId((Long) any())).thenThrow(new EmptyResultDataAccessException(3));
        when(productRepository.findProductByProductName((String) any())).thenThrow(new EmptyResultDataAccessException(3));
        when(productRepository.findAll()).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.allProducts("Name", 123L));
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
    @Test
    void testAllProducts3() throws ItemNotFoundException {
        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category());
        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(categoryList);
        when(productRepository.findProductByCategoryId((Long) any())).thenReturn(new ArrayList<>());
        when(productRepository.findProductByProductName((String) any())).thenReturn(new ArrayList<>());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(productServiceImp.allProducts("Name", 123L).isEmpty());
        verify(categoryRepository).findAllById((Iterable<Long>) any());
        verify(productRepository).findProductByCategoryId((Long) any());
        verify(productRepository).findProductByProductName((String) any());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
    @Test
    void testAllProducts4() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findProductByCategoryId((Long) any())).thenReturn(productList);
        when(productRepository.findProductByProductName((String) any())).thenReturn(new ArrayList<>());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user1);
        productResponse.setVerified(true);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setIsFullyVerified(true);
        user2.setIsVerified(true);
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setRoles(new HashSet<>());
        user2.setTelephoneNumber("4105551212");
        user2.setUserId(123L);
        user2.setUsername("janedoe");
        user2.setVerifiedBy("Verified By");

        ProductResponse productResponse1 = new ProductResponse();
        productResponse1.setCategory(new Category());
        productResponse1.setDescription("The characteristics of someone or something");
        productResponse1.setImageUrl("https://example.org/example");
        productResponse1.setItemCost(10.0d);
        productResponse1.setPrice(10.0d);
        productResponse1.setProductId(123L);
        productResponse1.setProductName("Product Name");
        productResponse1.setQty(1);
        productResponse1.setUser(user2);
        productResponse1.setVerified(true);
        when(productMapper.fromGetAllProductResponseToDomain((ProductResponse) any())).thenReturn(productResponse1);
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        assertEquals(1, productServiceImp.allProducts("Name", 123L).size());
        verify(categoryRepository).findAllById((Iterable<Long>) any());
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).findProductByCategoryId((Long) any());
        verify(productRepository).findProductByProductName((String) any());
        verify(productRepository).findAll();
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
        verify(productMapper).fromGetAllProductResponseToDomain((ProductResponse) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
    @Test
    void testAllProducts5() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findProductByCategoryId((Long) any())).thenReturn(productList);
        when(productRepository.findProductByProductName((String) any())).thenReturn(new ArrayList<>());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user1);
        productResponse.setVerified(true);
        when(productMapper.fromGetAllProductResponseToDomain((ProductResponse) any()))
                .thenThrow(new EmptyResultDataAccessException(3));
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.allProducts("Name", 123L));
        verify(categoryRepository).findAllById((Iterable<Long>) any());
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).findProductByCategoryId((Long) any());
        verify(productRepository).findProductByProductName((String) any());
        verify(productRepository).findAll();
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
        verify(productMapper).fromGetAllProductResponseToDomain((ProductResponse) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
    @Test
    void testAllProducts6() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        when(productRepository.findProductByCategoryId((Long) any())).thenReturn(productList);
        when(productRepository.findProductByProductName((String) any())).thenReturn(new ArrayList<>());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user1);
        productResponse.setVerified(true);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setIsFullyVerified(true);
        user2.setIsVerified(true);
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setRoles(new HashSet<>());
        user2.setTelephoneNumber("4105551212");
        user2.setUserId(123L);
        user2.setUsername("janedoe");
        user2.setVerifiedBy("Verified By");

        ProductResponse productResponse1 = new ProductResponse();
        productResponse1.setCategory(new Category());
        productResponse1.setDescription("The characteristics of someone or something");
        productResponse1.setImageUrl("https://example.org/example");
        productResponse1.setItemCost(10.0d);
        productResponse1.setPrice(10.0d);
        productResponse1.setProductId(123L);
        productResponse1.setProductName("Product Name");
        productResponse1.setQty(1);
        productResponse1.setUser(user2);
        productResponse1.setVerified(true);
        when(productMapper.fromGetAllProductResponseToDomain((ProductResponse) any())).thenReturn(productResponse1);
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        assertEquals(2, productServiceImp.allProducts("Name", 123L).size());
        verify(categoryRepository).findAllById((Iterable<Long>) any());
        verify(restTemplate, atLeast(1)).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).findProductByCategoryId((Long) any());
        verify(productRepository).findProductByProductName((String) any());
        verify(productRepository).findAll();
        verify(productMapper, atLeast(1)).fromCreateProductResponseToDomain((Product) any());
        verify(productMapper, atLeast(1)).fromGetAllProductResponseToDomain((ProductResponse) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#allProducts(String, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAllProducts7() throws ItemNotFoundException, RestClientException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cs490.shoppingCart.ProductManagementModule.model.Product.getCategoryId()" because "p" is null
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.lambda$allProducts$0(ProductServiceImp.java:124)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.allProducts(ProductServiceImp.java:124)
        //   In order to prevent allProducts(String, Long)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   allProducts(String, Long).
        //   See https://diff.blue/R013 to resolve this issue.

        when(categoryRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<Object>) any(), (Object[]) any())).thenReturn("For Object");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(null);
        when(productRepository.findProductByCategoryId((Long) any())).thenReturn(productList);
        when(productRepository.findProductByProductName((String) any())).thenReturn(new ArrayList<>());
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user1);
        productResponse.setVerified(true);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setIsFullyVerified(true);
        user2.setIsVerified(true);
        user2.setName("Name");
        user2.setPassword("iloveyou");
        user2.setRoles(new HashSet<>());
        user2.setTelephoneNumber("4105551212");
        user2.setUserId(123L);
        user2.setUsername("janedoe");
        user2.setVerifiedBy("Verified By");

        ProductResponse productResponse1 = new ProductResponse();
        productResponse1.setCategory(new Category());
        productResponse1.setDescription("The characteristics of someone or something");
        productResponse1.setImageUrl("https://example.org/example");
        productResponse1.setItemCost(10.0d);
        productResponse1.setPrice(10.0d);
        productResponse1.setProductId(123L);
        productResponse1.setProductName("Product Name");
        productResponse1.setQty(1);
        productResponse1.setUser(user2);
        productResponse1.setVerified(true);
        when(productMapper.fromGetAllProductResponseToDomain((ProductResponse) any())).thenReturn(productResponse1);
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        productServiceImp.allProducts("Name", 123L);
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
        user.setPassword("iloveyou");
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
        user1.setPassword("iloveyou");
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

    /**
     * Method under test: {@link ProductServiceImp#getProductById(Long)}
     */
    @Test
    void testGetProductById2() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findById((Long) any())).thenReturn(Optional.of(new Category()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        when(productMapper.fromCreateProductResponseToDomain((Product) any()))
                .thenThrow(new ItemNotFoundException("An error occurred"));
        assertThrows(ItemNotFoundException.class, () -> productServiceImp.getProductById(123L));
        verify(categoryRepository).findById((Long) any());
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).findById((Long) any());
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#getProductById(Long)}
     */
    @Test
    void testGetProductById3() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findById((Long) any())).thenReturn(Optional.empty());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
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
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
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
        assertNull(actualProductById.getCategory());
        assertEquals(user1, actualProductById.getUser());
        verify(categoryRepository).findById((Long) any());
        verify(restTemplate).getForObject((String) any(), (Class<User>) any(), (Object[]) any());
        verify(productRepository).findById((Long) any());
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#getProductById(Long)}
     */
    @Test
    void testGetProductById4() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findById((Long) any())).thenReturn(Optional.of(new Category()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<Object>) any(), (Object[]) any())).thenReturn("For Object");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        Product product = mock(Product.class);
        when(product.getCategoryId()).thenThrow(new EmptyResultDataAccessException(3));
        when(product.getUserId()).thenThrow(new EmptyResultDataAccessException(3));
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findById((Long) any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
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
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.getProductById(123L));
        verify(restTemplate).getForObject((String) any(), (Class<Object>) any(), (Object[]) any());
        verify(productRepository).findById((Long) any());
        verify(product).getCategoryId();
    }

    /**
     * Method under test: {@link ProductServiceImp#getProductById(Long)}
     */
    @Test
    void testGetProductById5() throws ItemNotFoundException, RestClientException {
        when(categoryRepository.findById((Long) any())).thenReturn(Optional.of(new Category()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");
        when(restTemplate.getForObject((String) any(), (Class<Object>) any(), (Object[]) any())).thenReturn("For Object");
        when(restTemplate.getForObject((String) any(), (Class<User>) any(), (Object[]) any())).thenReturn(user);
        when(productRepository.findById((Long) any())).thenReturn(Optional.empty());
        new EmptyResultDataAccessException(3);
        new EmptyResultDataAccessException(3);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setIsFullyVerified(true);
        user1.setIsVerified(true);
        user1.setName("Name");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setTelephoneNumber("4105551212");
        user1.setUserId(123L);
        user1.setUsername("janedoe");
        user1.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
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
        assertThrows(ItemNotFoundException.class, () -> productServiceImp.getProductById(123L));
        verify(restTemplate).getForObject((String) any(), (Class<Object>) any(), (Object[]) any());
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#deleteProductById(Long)}
     */
    @Test
    void testDeleteProductById() {
        doNothing().when(productRepository).deleteById((Long) any());
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        assertTrue(productServiceImp.deleteProductById(123L));
        verify(productRepository).findById((Long) any());
        verify(productRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#deleteProductById(Long)}
     */
    @Test
    void testDeleteProductById2() {
        doThrow(new EmptyResultDataAccessException(3)).when(productRepository).deleteById((Long) any());
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        assertFalse(productServiceImp.deleteProductById(123L));
        verify(productRepository).findById((Long) any());
        verify(productRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#deleteProductById(Long)}
     */
    @Test
    void testDeleteProductById3() {
        doNothing().when(productRepository).deleteById((Long) any());
        when(productRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertFalse(productServiceImp.deleteProductById(123L));
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#updateProduct(Product, Long)}
     */
    @Test
    void testUpdateProduct() throws IdNotMatchException, ItemNotFoundException {
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        assertThrows(IdNotMatchException.class, () -> productServiceImp.updateProduct(new Product(), 123L));
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#updateProduct(Product, Long)}
     */
    @Test
    void testUpdateProduct2() throws IdNotMatchException, ItemNotFoundException {
        when(productRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> productServiceImp.updateProduct(new Product(), 123L));
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#updateProduct(Product, Long)}
     */
    @Test
    void testUpdateProduct3() throws IdNotMatchException, ItemNotFoundException {
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user);
        productResponse.setVerified(true);
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        assertSame(productResponse,
                productServiceImp.updateProduct(new Product(123L, "Not match id from url with input id", 10.0d, 1, 10.0d,
                        "The characteristics of someone or something", "https://example.org/example", true, 123L, 123L), 123L));
        verify(productRepository).save((Product) any());
        verify(productRepository).findById((Long) any());
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#updateProduct(Product, Long)}
     */
    @Test
    void testUpdateProduct4() throws IdNotMatchException, ItemNotFoundException {
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        when(productMapper.fromCreateProductResponseToDomain((Product) any()))
                .thenThrow(new ItemNotFoundException("An error occurred"));
        assertThrows(ItemNotFoundException.class,
                () -> productServiceImp.updateProduct(new Product(123L, "Not match id from url with input id", 10.0d, 1, 10.0d,
                        "The characteristics of someone or something", "https://example.org/example", true, 123L, 123L), 123L));
        verify(productRepository).save((Product) any());
        verify(productRepository).findById((Long) any());
        verify(productMapper).fromCreateProductResponseToDomain((Product) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#updateProduct(Product, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateProduct5() throws IdNotMatchException, ItemNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cs490.shoppingCart.ProductManagementModule.model.Product.getProductId()" because "product" is null
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.updateProduct(ProductServiceImp.java:227)
        //   In order to prevent updateProduct(Product, Long)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   updateProduct(Product, Long).
        //   See https://diff.blue/R013 to resolve this issue.

        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setIsFullyVerified(true);
        user.setIsVerified(true);
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setTelephoneNumber("4105551212");
        user.setUserId(123L);
        user.setUsername("janedoe");
        user.setVerifiedBy("Verified By");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setCategory(new Category());
        productResponse.setDescription("The characteristics of someone or something");
        productResponse.setImageUrl("https://example.org/example");
        productResponse.setItemCost(10.0d);
        productResponse.setPrice(10.0d);
        productResponse.setProductId(123L);
        productResponse.setProductName("Product Name");
        productResponse.setQty(1);
        productResponse.setUser(user);
        productResponse.setVerified(true);
        when(productMapper.fromCreateProductResponseToDomain((Product) any())).thenReturn(productResponse);
        productServiceImp.updateProduct(null, 123L);
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
     * Method under test: {@link ProductServiceImp#verifiedProducts()}
     */
    @Test
    void testVerifiedProducts2() {
        when(productRepository.findAllByVerified(anyBoolean())).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.verifiedProducts());
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
     * Method under test: {@link ProductServiceImp#unverifiedProducts()}
     */
    @Test
    void testUnverifiedProducts2() {
        when(productRepository.findAllByVerified(anyBoolean())).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.unverifiedProducts());
        verify(productRepository).findAllByVerified(anyBoolean());
    }

    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    void testApproveProducts() {
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        assertTrue(productServiceImp.approveProducts(123L));
        verify(productRepository).save((Product) any());
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    void testApproveProducts2() {
        when(productRepository.save((Product) any())).thenThrow(new EmptyResultDataAccessException(3));
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.approveProducts(123L));
        verify(productRepository).save((Product) any());
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    void testApproveProducts3() {
        Product product = mock(Product.class);
        doThrow(new EmptyResultDataAccessException(3)).when(product).setVerified((Boolean) any());
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.approveProducts(123L));
        verify(productRepository).findById((Long) any());
        verify(product).setVerified((Boolean) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testApproveProducts4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.get()" because the return value of "com.cs490.shoppingCart.ProductManagementModule.repository.ProductRepository.findById(Object)" is null
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.approveProducts(ProductServiceImp.java:264)
        //   In order to prevent approveProducts(Long)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   approveProducts(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(null);
        new EmptyResultDataAccessException(3);
        productServiceImp.approveProducts(123L);
    }

    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    void testApproveProducts5() {
        Product product = mock(Product.class);
        doThrow(new EmptyResultDataAccessException(3)).when(product).setVerified((Boolean) any());
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findAllByVerified(anyBoolean())).thenReturn(new ArrayList<>());
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(productServiceImp.approveProducts(null));
        verify(productRepository).findAllByVerified(anyBoolean());
    }

    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    void testApproveProducts6() {
        Product product = mock(Product.class);
        doThrow(new EmptyResultDataAccessException(3)).when(product).setVerified((Boolean) any());
        Optional<Product> ofResult = Optional.of(product);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findAllByVerified(anyBoolean())).thenReturn(productList);
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(ofResult);
        assertTrue(productServiceImp.approveProducts(null));
        verify(productRepository).save((Product) any());
        verify(productRepository).findAllByVerified(anyBoolean());
    }

    /**
     * Method under test: {@link ProductServiceImp#approveProducts(Long)}
     */
    @Test
    void testApproveProducts7() {
        Product product = mock(Product.class);
        doThrow(new EmptyResultDataAccessException(3)).when(product).setVerified((Boolean) any());
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findAllByVerified(anyBoolean())).thenThrow(new EmptyResultDataAccessException(3));
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.approveProducts(null));
        verify(productRepository).findAllByVerified(anyBoolean());
    }

    /**
     * Method under test: {@link ProductServiceImp#uploadFile(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUploadFile() throws UnsupportedEncodingException {
        // TODO: Complete this test.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access files outside the temporary directory (file '', permission 'write').
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        productServiceImp.uploadFile(new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8")));
    }

    /**
     * Method under test: {@link ProductServiceImp#uploadFile(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUploadFile2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.web.multipart.MultipartFile.getOriginalFilename()" because "file" is null
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.convertMultiPartFileToFile(ProductServiceImp.java:312)
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.uploadFile(ProductServiceImp.java:284)
        //   In order to prevent uploadFile(MultipartFile)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   uploadFile(MultipartFile).
        //   See https://diff.blue/R013 to resolve this issue.

        productServiceImp.uploadFile(null);
    }

    /**
     * Method under test: {@link ProductServiceImp#uploadFile(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUploadFile3() {
        // TODO: Complete this test.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access files outside the temporary directory (file 'foo.txt', permission 'write').
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        MockMultipartFile mockMultipartFile = mock(MockMultipartFile.class);
        when(mockMultipartFile.getOriginalFilename()).thenReturn("foo.txt");
        productServiceImp.uploadFile(mockMultipartFile);
    }

    /**
     * Method under test: {@link ProductServiceImp#downloadFile(String)}
     */
    @Test
    void testDownloadFile() throws SdkClientException, UnsupportedEncodingException {
        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(new StringInputStream("Lorem ipsum dolor sit amet."));
        when(amazonS3.getObject((String) any(), (String) any())).thenReturn(s3Object);
        assertEquals(27, productServiceImp.downloadFile("foo.txt").length);
        verify(amazonS3).getObject((String) any(), (String) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#downloadFile(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDownloadFile2() throws SdkClientException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.io.InputStream.read(byte[], int, int)" because "this.in" is null
        //       at java.io.DataInputStream.read(DataInputStream.java:151)
        //       at com.amazonaws.internal.SdkFilterInputStream.read(SdkFilterInputStream.java:90)
        //       at java.io.FilterInputStream.read(FilterInputStream.java:106)
        //       at com.amazonaws.util.IOUtils.toByteArray(IOUtils.java:44)
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.downloadFile(ProductServiceImp.java:296)
        //   In order to prevent downloadFile(String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   downloadFile(String).
        //   See https://diff.blue/R013 to resolve this issue.

        S3Object s3Object = new S3Object();
        s3Object.setObjectContent(mock(DataInputStream.class));
        when(amazonS3.getObject((String) any(), (String) any())).thenReturn(s3Object);
        productServiceImp.downloadFile("foo.txt");
    }

    /**
     * Method under test: {@link ProductServiceImp#deleteFile(String)}
     */
    @Test
    void testDeleteFile() throws SdkClientException {
        doNothing().when(amazonS3).deleteObject((String) any(), (String) any());
        assertEquals("foo.txt removed ...", productServiceImp.deleteFile("foo.txt"));
        verify(amazonS3).deleteObject((String) any(), (String) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#deleteFile(String)}
     */
    @Test
    void testDeleteFile2() throws SdkClientException {
        doThrow(new EmptyResultDataAccessException(3)).when(amazonS3).deleteObject((String) any(), (String) any());
        assertThrows(EmptyResultDataAccessException.class, () -> productServiceImp.deleteFile("foo.txt"));
        verify(amazonS3).deleteObject((String) any(), (String) any());
    }
}

