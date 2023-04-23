package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import com.cs490.shoppingCart.PaymentModule.repository.VisaCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.imp.VisaCardServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisaCardServiceTest {

    @Mock
    private VisaCardRepository visaCardRepository;

    @InjectMocks
    private VisaCardServiceImp visaCardService;

    @Test
    public void testGetVisaDetail() {
        CardDetail cardDetail = new CardDetail();
        cardDetail.setCardNumber("4111111111111111");
        cardDetail.setName("Selam");
        cardDetail.setCCV("302");
        cardDetail.setCardExpiry("1222");

        VisaCard visaCard = new VisaCard();
        visaCard.setCardNumber(cardDetail.getCardNumber());
        visaCard.setName(cardDetail.getName());
        visaCard.setCCV(cardDetail.getCCV());
        visaCard.setCardExpiry(LocalDate.of(2022, 12, 31));
        visaCard.setCardValue(100.0);

        when(visaCardRepository.getVisaCardByCardNumberAndNameAndCCV(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        )).thenReturn(visaCard);

        VisaCard result = visaCardService.getVisaDetail(cardDetail);

        assertNotNull(result);
        assertEquals(visaCard, result);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
        String formattedDate = visaCard.getCardExpiry().format(formatter);

        assertEquals(visaCard.getCardValue(), result.getCardValue());
        assertEquals(formattedDate, cardDetail.getCardExpiry());

        verify(visaCardRepository, times(1)).getVisaCardByCardNumberAndNameAndCCV(
                cardDetail.getCardNumber(),
                cardDetail.getName(),
                cardDetail.getCCV()
        );
    }
}
