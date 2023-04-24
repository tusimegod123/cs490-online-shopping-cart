package com.cs490.shoppingCart.PaymentModule.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequest;
import com.cs490.shoppingCart.PaymentModule.DTO.RegistrationPayment;
import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import com.cs490.shoppingCart.PaymentModule.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

import com.cs490.shoppingCart.PaymentModule.service.imp.PaymentServiceImp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(classes = {PaymentServiceImp.class})
@ExtendWith(SpringExtension.class)
class PaymentServiceTest {
    @MockBean
    private BankService bankService;

    @Autowired
    private PaymentServiceImp paymentServiceImp;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private TransactionRepository transactionRepository;


    /**
     * Method under test: {@link PaymentServiceImp#processOrderPayment(PaymentRequest)}
     */
    @Test
    void testProcessOrderPayment() throws Exception {
        // Arrange
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCardNumber("42");

        // Act and Assert
        assertThrows(Exception.class, () -> paymentServiceImp.processOrderPayment(paymentRequest));
    }

    /**
     * Method under test: {@link PaymentServiceImp#processRegistrationPayment(RegistrationPayment)}
     */
    @Test
    void testProcessRegistrationPayment() throws Exception {
        // Arrange
        RegistrationPayment registrationPayment = new RegistrationPayment();
        registrationPayment.setCardNumber("42");

        // Act and Assert
        assertThrows(Exception.class, () -> paymentServiceImp.processRegistrationPayment(registrationPayment));
    }


    /**
     * Method under test: {@link PaymentServiceImp#getAll()}
     */
    @Test
    void testGetAll() {
        // Arrange
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(transactionRepository.findAll()).thenReturn(transactionList);

        // Act
        List<Transaction> actualAll = paymentServiceImp.getAll();

        // Assert
        assertSame(transactionList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(transactionRepository).findAll();
    }

    /**
     * Method under test: {@link PaymentServiceImp#findByUserId(Long)}
     */
    @Test
    void testFindByUserId() {
        // Arrange
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(transactionRepository.findTransactionsByUserId((Long) any())).thenReturn(transactionList);

        // Act
        List<Transaction> actualFindByUserIdResult = paymentServiceImp.findByUserId(123L);

        // Assert
        assertSame(transactionList, actualFindByUserIdResult);
        assertTrue(actualFindByUserIdResult.isEmpty());
        verify(transactionRepository).findTransactionsByUserId((Long) any());
    }

    /**
     * Method under test: {@link PaymentServiceImp#findByOrderId(Long)}
     */
    @Test
    void testFindByOrderId() {
        // Arrange
        Transaction transaction = new Transaction();
        when(transactionRepository.findTransactionByOrderId((Long) any())).thenReturn(transaction);

        // Act and Assert
        assertSame(transaction, paymentServiceImp.findByOrderId(123L));
        verify(transactionRepository).findTransactionByOrderId((Long) any());
    }
}

