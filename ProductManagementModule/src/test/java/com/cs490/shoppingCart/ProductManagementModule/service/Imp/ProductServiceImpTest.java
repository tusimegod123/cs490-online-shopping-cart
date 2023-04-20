package com.cs490.shoppingCart.ProductManagementModule.service.Imp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import com.cs490.shoppingCart.ProductManagementModule.dto.CategoryResponse;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductRequest;
import com.cs490.shoppingCart.ProductManagementModule.dto.ProductResponse;
import com.cs490.shoppingCart.ProductManagementModule.exception.ItemNotFoundException;
import com.cs490.shoppingCart.ProductManagementModule.mapper.CategoryMapper;
import com.cs490.shoppingCart.ProductManagementModule.mapper.ProductMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Category;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.model.User;
import com.cs490.shoppingCart.ProductManagementModule.repository.CategoryRepository;
import com.cs490.shoppingCart.ProductManagementModule.repository.ProductRepository;
import com.cs490.shoppingCart.ProductManagementModule.service.CategoryService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    void testApproveProducts() {
        when(productRepository.save((Product) any())).thenReturn(new Product());
        when(productRepository.findById((Long) any())).thenReturn(Optional.of(new Product()));
        assertTrue(productServiceImp.approveProducts(123L));
        verify(productRepository).save((Product) any());
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    void testGetAllProductWithSpecificIDList() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(productServiceImp.getAllProductWithSpecificIDList(new HashSet<>()).isEmpty());
        verify(productRepository).findAll();
    }



}

