package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.BankResponse;
import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.DTO.PaymentType;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;

import com.cs490.shoppingCart.PaymentModule.repository.MasterCardRepository;
import com.cs490.shoppingCart.PaymentModule.repository.VisaCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.imp.BankServiceImp;
import com.cs490.shoppingCart.PaymentModule.service.imp.MasterCardServiceImp;
import com.cs490.shoppingCart.PaymentModule.service.imp.VisaCardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BankServiceImpTest {

    @Autowired
    @InjectMocks
    private VisaCardServiceImp visaCardService;

    @Autowired
    @InjectMocks
    private MasterCardServiceImp masterCardService;


    @Autowired
    @InjectMocks
    private BankServiceImp bankService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @MockBean
    private VisaCardRepository visaCardRepository;

    @MockBean
    private MasterCardRepository masterCardRepository;

    @Test
    public void testProcessCardForMasterCard() throws Exception {
        CardDetail cardDetail = new CardDetail("5123456789012345", "Selam","222", "0425");

        // Set up the expected output
        MasterCard masterCard = new MasterCard();
        masterCard.setCardNumber("5123456789012345");
        masterCard.setCCV("222");
        masterCard.setCardExpiry(LocalDate.of(2025, 04, 18));

        BankResponse expectedResponse = new BankResponse();
        expectedResponse.setPaymentType(PaymentType.MASTER);
        expectedResponse.setCurrentBalance(masterCard.getCardValue());

        when(masterCardRepository.getMasterCardByCardNumberAndNameAndCCV(cardDetail.getCardNumber(),
                cardDetail.getName(),
                cardDetail.getCCV()))
                .thenReturn(masterCard);

        // Set up the mock behavior
        Mockito.when(masterCardService.getMasterDetail(cardDetail)).thenReturn(masterCard);

        // Verify the output
        BankResponse actualResponse = bankService.processCard(cardDetail);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testProcessCardForVisaCard() throws Exception {
        CardDetail cardDetail = new CardDetail("4123456789012345", "Selam","344", "0425");

        // Set up the expected output
        VisaCard visaCard = new VisaCard();
        visaCard.setCardNumber("4123456789012345");
        visaCard.setCCV("344");
        visaCard.setCardExpiry(LocalDate.of(2025, 04, 18));

        BankResponse expectedResponse = new BankResponse();
        expectedResponse.setPaymentType(PaymentType.VISA);
        expectedResponse.setCurrentBalance(visaCard.getCardValue());

        // Set up mock repository
        when(visaCardRepository.getVisaCardByCardNumberAndNameAndCCV(cardDetail.getCardNumber(),
                        cardDetail.getName(),
                        cardDetail.getCCV()))
                .thenReturn(visaCard);


        // Set up the mock behavior
        Mockito.when(visaCardService.getVisaDetail(cardDetail)).thenReturn(visaCard);

        // Verify the output
        BankResponse actualResponse = bankService.processCard(cardDetail);
        assertEquals(expectedResponse, actualResponse);
    }
}

