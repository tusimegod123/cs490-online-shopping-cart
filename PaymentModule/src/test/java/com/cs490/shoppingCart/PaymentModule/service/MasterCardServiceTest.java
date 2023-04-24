package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.repository.MasterCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.imp.MasterCardServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MasterCardServiceTest {

    @Mock
    private MasterCardRepository masterCardRepository;

    @InjectMocks
    private MasterCardServiceImp masterCardService;

    @Test
    public void testGetMasterDetail() {
        CardDetail cardDetail = new CardDetail();
        cardDetail.setCardNumber("5567567867897891");
        cardDetail.setName("Selam");
        cardDetail.setCCV("302");
        cardDetail.setCardExpiry("1222");

        MasterCard masterCard = new MasterCard();
        masterCard.setCardNumber(cardDetail.getCardNumber());
        masterCard.setName(cardDetail.getName());
        masterCard.setCCV(cardDetail.getCCV());
        masterCard.setCardExpiry(LocalDate.of(2022, 12, 31));
        masterCard.setCardValue(100.0);

        when(masterCardRepository.getMasterCardByCardNumberAndNameAndCCV(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        )).thenReturn(masterCard);

        MasterCard result = masterCardService.getMasterDetail(cardDetail);

        assertNotNull(result);
        assertEquals(masterCard, result);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
        String formattedDate = masterCard.getCardExpiry().format(formatter);

        assertEquals(masterCard.getCardValue(), result.getCardValue());
        assertEquals(formattedDate, cardDetail.getCardExpiry());

        verify(masterCardRepository, times(1)).getMasterCardByCardNumberAndNameAndCCV(
                cardDetail.getCardNumber(),
                cardDetail.getName(),
                cardDetail.getCCV()
        );

    }
}
