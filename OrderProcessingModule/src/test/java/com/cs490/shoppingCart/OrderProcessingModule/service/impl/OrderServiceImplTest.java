package com.cs490.shoppingCart.OrderProcessingModule.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs490.shoppingCart.OrderProcessingModule.dto.CartLine;
import com.cs490.shoppingCart.OrderProcessingModule.dto.GuestOrderRequest;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.OrderRequestDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.PaymentInfoDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.ShoppingCartDTO;
import com.cs490.shoppingCart.OrderProcessingModule.dto.UserDTO;
import com.cs490.shoppingCart.OrderProcessingModule.model.Order;
import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;
import com.cs490.shoppingCart.OrderProcessingModule.model.OrderStatus;
import com.cs490.shoppingCart.OrderProcessingModule.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {OrderServiceImpl.class, ModelMapper.class})
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {
    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @MockBean
    private RestTemplate restTemplate;

    /**
     * Method under test: {@link OrderServiceImpl#getOrders()}
     */
    @Test
    void testGetOrders() {
        // Arrange
        ArrayList<Order> orderList = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orderList);

        // Act
        List<Order> actualOrders = orderServiceImpl.getOrders();

        // Assert
        assertSame(orderList, actualOrders);
        assertTrue(actualOrders.isEmpty());
        verify(orderRepository).findAll();
    }








    /**
     * Method under test: {@link OrderServiceImpl#createOrder(OrderRequestDTO)}
     */
    @Test
    void testCreateOrder4() throws RestClientException {
        // Arrange
        when(orderRepository.save((Order) any())).thenReturn(new Order());
        when(restTemplate.getForObject((String) any(), (Class<UserDTO>) any(), (Object[]) any()))
                .thenThrow(new RuntimeException("An error occurred"));

        HashSet<CartLine> cartLineSet = new HashSet<>();
        cartLineSet.add(new CartLine());

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setCartLines(cartLineSet);

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setShoppingCart(shoppingCartDTO);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> orderServiceImpl.createOrder(orderRequestDTO));
        verify(restTemplate).getForObject((String) any(), (Class<UserDTO>) any(), (Object[]) any());
    }


    /**
     * Method under test: {@link OrderServiceImpl#checkOrderExistance(Long)}
     */
    @Test
    void testCheckOrderExistance() {
        // Arrange
        when(orderRepository.existsById((Long) any())).thenReturn(true);

        // Act and Assert
        assertTrue(orderServiceImpl.checkOrderExistance(123L));
        verify(orderRepository).existsById((Long) any());
    }


    /**
     * Method under test: {@link OrderServiceImpl#getOrder(Long)}
     */
    @Test
    void testGetOrder() {
        // Arrange
        Order order = new Order();
        when(orderRepository.findById((Long) any())).thenReturn(Optional.of(order));

        // Act and Assert
        assertSame(order, orderServiceImpl.getOrder(123L));
        verify(orderRepository).findById((Long) any());
    }




    /**
     * Method under test: {@link OrderServiceImpl#createGuestOrder(GuestOrderRequest)}
     */
    @Test
    void testCreateGuestOrder() throws RestClientException {
        // Arrange
        when(restTemplate.postForObject((String) any(), (Object) any(), (Class<UserDTO>) any(), (Object[]) any()))
                .thenThrow(new RuntimeException("An error occurred"));

        GuestOrderRequest guestOrderRequest = new GuestOrderRequest();
        guestOrderRequest.setUserInfo(new UserDTO("Name", "jane.doe@example.org", "4105551212", new ArrayList<>()));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> orderServiceImpl.createGuestOrder(guestOrderRequest));
        verify(restTemplate).postForObject((String) any(), (Object) any(), (Class<UserDTO>) any(), (Object[]) any());
    }


    /**
     * Method under test: {@link OrderServiceImpl#getOrdersForUser(Long)}
     */
    @Test
    void testGetOrdersForUser() {
        // Arrange
        ArrayList<Order> orderList = new ArrayList<>();
        when(orderRepository.findAllByUserIdEquals((Long) any())).thenReturn(orderList);

        // Act
        List<Order> actualOrdersForUser = orderServiceImpl.getOrdersForUser(123L);

        // Assert
        assertSame(orderList, actualOrdersForUser);
        assertTrue(actualOrdersForUser.isEmpty());
        verify(orderRepository).findAllByUserIdEquals((Long) any());
    }



    /**
     * Method under test: {@link OrderServiceImpl#checkOrderStatusIsNotSuccessful(Long)}
     */
    @Test
    void testCheckOrderStatusIsNotSuccessful() {
        // Arrange
        when(orderRepository.save((Order) any())).thenReturn(new Order());
        when(orderRepository.findById((Long) any())).thenReturn(Optional.of(new Order()));

        // Act and Assert
        assertTrue(orderServiceImpl.checkOrderStatusIsNotSuccessful(123L));
        verify(orderRepository).save((Order) any());
        verify(orderRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link OrderServiceImpl#getAllOrdersForReport(LocalDate, LocalDate, Long)}
     */
    @Test
    void testGetAllOrdersForReport() {
        // Arrange
        when(orderRepository.findAllByOrderDateBetween((LocalDateTime) any(), (LocalDateTime) any()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertTrue(
                orderServiceImpl.getAllOrdersForReport(LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(1L), 123L).isEmpty());
        verify(orderRepository).findAllByOrderDateBetween((LocalDateTime) any(), (LocalDateTime) any());
    }

}

