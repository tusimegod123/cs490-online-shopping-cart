package com.cs490.shoppingCart.PaymentModule.controller;

import com.cs490.shoppingCart.PaymentModule.DTO.PaymentRequest;
import com.cs490.shoppingCart.PaymentModule.DTO.PaymentType;
import com.cs490.shoppingCart.PaymentModule.DTO.RegistrationPayment;
import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;
import com.cs490.shoppingCart.PaymentModule.model.TransactionType;
import com.cs490.shoppingCart.PaymentModule.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    private PaymentController paymentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        paymentController = new PaymentController(paymentService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void makePaymentTest() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setUserId(1L);
        request.setAmount(100.0);

        when(paymentService.processOrderPayment(any(PaymentRequest.class)))
                .thenReturn(TransactionStatus.TS);

        mockMvc.perform(post("/api/v1/payments/payOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"amount\": 100 }"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"TS\""));
    }

    @Test
    public void registrationFeeTest() throws Exception {
        RegistrationPayment request = new RegistrationPayment();
        request.setUserId(1L);
        request.setAmount(50.0);

        when(paymentService.processRegistrationPayment(any(RegistrationPayment.class)))
                .thenReturn(TransactionStatus.TS);

        mockMvc.perform(post("/api/v1/payments/payRegistrationFee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userId\": 1, \"amount\": 50 }"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"TS\""));
    }

    @Test
    void testGetAllTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, 1L, 1L, "5567567867897891", 100.0,
                10.0, TransactionStatus.TS, "1234-5678-9012",
                new Date(), PaymentType.MASTER, TransactionType.OrderPayment));

        when(paymentService.getAll()).thenReturn(transactions);
        mockMvc.perform(get("/api/v1/payments/getAllTransactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetAllTransactionsByUser() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, 1L, 1L, "5567567867897891", 100.0,
                10.0, TransactionStatus.TS, "1234-5678-9012",
                new Date(), PaymentType.MASTER, TransactionType.OrderPayment));

        when(paymentService.findByUserId(1l)).thenReturn(transactions);
        mockMvc.perform(get("/api/v1/payments/getTransactionsByUserId/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetAllTransactionsByOrder() throws Exception {
        Transaction transaction = new Transaction(1L, 1L, 1L, "5567567867897891", 100.0,
                10.0, TransactionStatus.TS, "1234-5678-9012",
                new Date(), PaymentType.MASTER, TransactionType.OrderPayment);

        when(paymentService.findByOrderId(1l)).thenReturn(transaction);
        mockMvc.perform(get("/api/v1/payments/getTransactionByOrderId/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cardNumber").value("5567567867897891"));
    }

}

