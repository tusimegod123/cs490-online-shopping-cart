package ecommerce.shoppingcartservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import ecommerce.shoppingcartservice.model.dto.ProductDTO;
import ecommerce.shoppingcartservice.model.dto.RequestModel;
import ecommerce.shoppingcartservice.model.dto.ShoppingCartDTO;
import ecommerce.shoppingcartservice.repository.CartLineRepository;
import ecommerce.shoppingcartservice.repository.ShoppingCartRepository;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {ShoppingCartServiceImpl.class, ModelMapper.class})
@ExtendWith(SpringExtension.class)
class ShoppingCartServiceImplTest {
    @MockBean
    private CartLineRepository cartLineRepository;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    /**
     * Method under test: {@link ShoppingCartServiceImpl#getCartItems(Long)}
     */
    @Test
    void testGetCartItems() {
        // Arrange
        when(shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean()))
                .thenReturn(new ShoppingCart());

        // Act
        ShoppingCartDTO actualCartItems = shoppingCartServiceImpl.getCartItems(123L);

        // Assert
        assertNull(actualCartItems.getCartLines());
        assertNull(actualCartItems.getUserId());
        assertNull(actualCartItems.getTotalPrice());
        assertNull(actualCartItems.getId());
        verify(shoppingCartRepository).findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean());
    }






    /**
     * Method under test: {@link ShoppingCartServiceImpl#addToCart(RequestModel)}
     */
    @Test
    void testAddToCart() {
        // Arrange
        HashSet<CartLine> cartLineSet = new HashSet<>();
        cartLineSet.add(new CartLine());
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        doNothing().when(shoppingCart).setTotalPrice((Double) any());
        when(shoppingCart.getCartLines()).thenReturn(new HashSet<>());
        doNothing().when(shoppingCart).setCartLines((Set<CartLine>) any());
        shoppingCart.setCartLines(cartLineSet);
        when(shoppingCartRepository.save((ShoppingCart) any())).thenReturn(new ShoppingCart());
        when(shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean()))
                .thenReturn(shoppingCart);
        ProductDTO productDTO = mock(ProductDTO.class);
        when(productDTO.getPrice()).thenReturn(10.0d);
        when(productDTO.getProductId()).thenReturn(123);
        when(productDTO.getUserId()).thenReturn(123L);
        when(productDTO.getDescription()).thenReturn("The characteristics of someone or something");
        when(productDTO.getImageUrl()).thenReturn("https://example.org/example");
        when(productDTO.getProductName()).thenReturn("Product Name");
        doNothing().when(productDTO).setPrice((Double) any());
        doNothing().when(productDTO).setProductId((Integer) any());
        productDTO.setPrice(null);
        productDTO.setProductId(123);

        RequestModel requestModel = new RequestModel();
        requestModel.setQuantity(1);
        requestModel.setProductDTO(productDTO);

        // Act
        ShoppingCartDTO actualAddToCartResult = shoppingCartServiceImpl.addToCart(requestModel);

        // Assert
        assertNull(actualAddToCartResult.getCartLines());
        assertNull(actualAddToCartResult.getUserId());
        assertNull(actualAddToCartResult.getTotalPrice());
        assertNull(actualAddToCartResult.getId());
        verify(shoppingCartRepository, atLeast(1)).findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(),
                anyBoolean());
        verify(shoppingCartRepository).save((ShoppingCart) any());
        verify(shoppingCart, atLeast(1)).getCartLines();
        verify(shoppingCart, atLeast(1)).setCartLines((Set<CartLine>) any());
        verify(shoppingCart).setTotalPrice((Double) any());
        verify(productDTO, atLeast(1)).getPrice();
        verify(productDTO, atLeast(1)).getProductId();
        verify(productDTO).getUserId();
        verify(productDTO).getDescription();
        verify(productDTO).getImageUrl();
        verify(productDTO).getProductName();
        verify(productDTO).setPrice((Double) any());
        verify(productDTO).setProductId((Integer) any());
    }


    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkCartExistForUser(Long)}
     */
    @Test
    void testCheckCartExistForUser() {
        // Arrange
        when(shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean()))
                .thenReturn(new ShoppingCart());

        // Act and Assert
        assertTrue(shoppingCartServiceImpl.checkCartExistForUser(123L));
        verify(shoppingCartRepository).findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean());
    }


    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkCartExistForUserAndStatusFalse(Long)}
     */
    @Test
    void testCheckCartExistForUserAndStatusFalse() {
        // Arrange
        when(shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean()))
                .thenReturn(new ShoppingCart());

        // Act and Assert
        assertTrue(shoppingCartServiceImpl.checkCartExistForUserAndStatusFalse(123L));
        verify(shoppingCartRepository).findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkCartExistForUserAndStatusFalse(Long)}
     */
    @Test
    void testCheckCartExistForUserAndStatusFalse2() {
        // Arrange
        when(shoppingCartRepository.findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean()))
                .thenReturn(null);

        // Act and Assert
        assertFalse(shoppingCartServiceImpl.checkCartExistForUserAndStatusFalse(123L));
        verify(shoppingCartRepository).findShoppingCartByUserIdEqualsAndCartStatusEquals((Long) any(), anyBoolean());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkOut(Long)}
     */
    @Test
    void testCheckOut() {
        // Arrange
        when(shoppingCartRepository.save((ShoppingCart) any())).thenReturn(new ShoppingCart());
        when(shoppingCartRepository.findById((Long) any())).thenReturn(Optional.of(new ShoppingCart()));

        // Act
        ShoppingCartDTO actualCheckOutResult = shoppingCartServiceImpl.checkOut(123L);

        // Assert
        assertNull(actualCheckOutResult.getCartLines());
        assertNull(actualCheckOutResult.getUserId());
        assertNull(actualCheckOutResult.getTotalPrice());
        assertNull(actualCheckOutResult.getId());
        verify(shoppingCartRepository).save((ShoppingCart) any());
        verify(shoppingCartRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ShoppingCartServiceImpl#checkCartExistance(Long)}
     */
    @Test
    void testCheckCartExistance() {
        // Arrange
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        when(shoppingCart.getCartStatus()).thenReturn(true);
        Optional<ShoppingCart> ofResult = Optional.of(shoppingCart);
        when(shoppingCartRepository.findById((Long) any())).thenReturn(ofResult);
        when(shoppingCartRepository.existsById((Long) any())).thenReturn(false);

        // Act and Assert
        assertFalse(shoppingCartServiceImpl.checkCartExistance(123L));
        verify(shoppingCartRepository).existsById((Long) any());
    }


}

