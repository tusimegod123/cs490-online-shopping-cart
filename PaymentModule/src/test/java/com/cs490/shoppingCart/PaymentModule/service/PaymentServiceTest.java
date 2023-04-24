package com.cs490.shoppingCart.PaymentModule.service;


import com.cs490.shoppingCart.PaymentModule.DTO.*;
import com.cs490.shoppingCart.PaymentModule.repository.TransactionRepository;
import com.cs490.shoppingCart.PaymentModule.service.imp.BankServiceImp;
import com.cs490.shoppingCart.PaymentModule.service.imp.PaymentServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentServiceTest {

    @InjectMocks
    private BankServiceImp bankService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentServiceImp paymentServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testProcessOrderPaymentSuccessful() throws Exception {
//        PaymentRequest paymentRequest = new PaymentRequest();
//        paymentRequest.setUserId(1L);
//        paymentRequest.setOrderId(1L);
//        paymentRequest.setAmount(100.0);
//        paymentRequest.setCardNumber("4111111111111111");
//        CardDetail cardDetail = new CardDetail();
//        cardDetail.setCardNumber(paymentRequest.getCardNumber());
//        when(bankService.processCard(cardDetail)).thenReturn(new BankResponse(PaymentType.MASTER, 1000.0));
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
//        TransactionStatus result = paymentServiceImp.processOrderPayment(paymentRequest);
//        assertEquals(TransactionStatus.TS, result);
//    }
//
//    @Test
//    void testProcessOrderPaymentInsufficientBalance() throws Exception {
//        PaymentRequest paymentRequest = new PaymentRequest();
//        paymentRequest.setUserId(1L);
//        paymentRequest.setOrderId(1L);
//        paymentRequest.setAmount(100.0);
//        paymentRequest.setCardNumber("4111111111111111");
//        CardDetail cardDetail = new CardDetail();
//        cardDetail.setCardNumber(paymentRequest.getCardNumber());
//        when(bankService.processCard(cardDetail)).thenReturn(new BankResponse(PaymentType.MASTER, 50.0));
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
//        try {
//            paymentServiceImp.processOrderPayment(paymentRequest);
//        } catch (Exception e) {
//            assertEquals("Transaction failed, the card doesn't have enough balance to perform the transaction.", e.getMessage());
//        }
//    }

    @Test
    void testProcessOrderPaymentInvalidCardNumber() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserId(1L);
        paymentRequest.setOrderId(1L);
        paymentRequest.setAmount(100.0);
        paymentRequest.setCardNumber("123");
        try {
            paymentServiceImp.processOrderPayment(paymentRequest);
        } catch (Exception e) {
            assertEquals("The system only support 16 digit card number and MASTER/VISA card type only.", e.getMessage());
        }
    }

}