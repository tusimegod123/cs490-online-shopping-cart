package com.cs490.shoppingCart.ProductManagementModule.service.Imp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs490.shoppingCart.ProductManagementModule.mapper.CategoryMapper;
import com.cs490.shoppingCart.ProductManagementModule.mapper.ProductMapper;
import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import com.cs490.shoppingCart.ProductManagementModule.repository.CategoryRepository;
import com.cs490.shoppingCart.ProductManagementModule.repository.ProductRepository;
import com.cs490.shoppingCart.ProductManagementModule.service.CategoryService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {ProductServiceImp.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImpTest {
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
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    void testGetAllProductWithSpecificIDList() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(productServiceImp.getAllProductWithSpecificIDList(new HashSet<>()).isEmpty());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    void testGetAllProductWithSpecificIDList2() {
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList);
        assertTrue(productServiceImp.getAllProductWithSpecificIDList(new HashSet<>()).isEmpty());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    void testGetAllProductWithSpecificIDList3() {
        when(productRepository.findAll()).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class,
                () -> productServiceImp.getAllProductWithSpecificIDList(new HashSet<>()));
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllProductWithSpecificIDList4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cs490.shoppingCart.ProductManagementModule.model.Product.getProductId()" because "product" is null
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.getAllProductWithSpecificIDList(ProductServiceImp.java:326)
        //   In order to prevent getAllProductWithSpecificIDList(Set)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getAllProductWithSpecificIDList(Set).
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(null);
        when(productRepository.findAll()).thenReturn(productList);
        productServiceImp.getAllProductWithSpecificIDList(new HashSet<>());
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    void testGetAllProductWithSpecificIDList5() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(productServiceImp.getAllProductWithSpecificIDList(new HashSet<>()).isEmpty());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    void testGetAllProductWithSpecificIDList6() {
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList);
        assertTrue(productServiceImp.getAllProductWithSpecificIDList(new HashSet<>()).isEmpty());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    void testGetAllProductWithSpecificIDList7() {
        when(productRepository.findAll()).thenThrow(new EmptyResultDataAccessException(3));
        assertThrows(EmptyResultDataAccessException.class,
                () -> productServiceImp.getAllProductWithSpecificIDList(new HashSet<>()));
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImp#getAllProductWithSpecificIDList(Set)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllProductWithSpecificIDList8() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cs490.shoppingCart.ProductManagementModule.model.Product.getProductId()" because "product" is null
        //       at com.cs490.shoppingCart.ProductManagementModule.service.Imp.ProductServiceImp.getAllProductWithSpecificIDList(ProductServiceImp.java:326)
        //   In order to prevent getAllProductWithSpecificIDList(Set)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getAllProductWithSpecificIDList(Set).
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(null);
        when(productRepository.findAll()).thenReturn(productList);
        productServiceImp.getAllProductWithSpecificIDList(new HashSet<>());
    }
}

