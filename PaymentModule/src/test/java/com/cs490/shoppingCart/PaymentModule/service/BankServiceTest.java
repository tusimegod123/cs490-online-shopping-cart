package com.cs490.shoppingCart.PaymentModule.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs490.shoppingCart.PaymentModule.DTO.BankResponse;
import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.DTO.PaymentType;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import com.cs490.shoppingCart.PaymentModule.service.imp.BankServiceImp;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BankServiceImp.class})
@ExtendWith(SpringExtension.class)
class BankServiceTest {
    @Autowired
    private BankServiceImp bankServiceImp;

    @MockBean
    private MasterCardService masterCardService;

    @MockBean
    private VisaCardService visaCardService;

    /**
     * Method under test: {@link BankServiceImp#processCard(CardDetail)}
     */
    @Test
    void testProcessCard() throws Exception {
        // Arrange
        when(visaCardService.getVisaDetail((CardDetail) any())).thenReturn(new VisaCard());

        // Act
        BankResponse actualProcessCardResult = bankServiceImp
                .processCard(new CardDetail("42", "Name", "CCV", "Card Expiry"));

        // Assert
        assertNull(actualProcessCardResult.getCurrentBalance());
        assertEquals(PaymentType.VISA, actualProcessCardResult.getPaymentType());
        verify(visaCardService).getVisaDetail((CardDetail) any());
    }

    /**
     * Method under test: {@link BankServiceImp#processCard(CardDetail)}
     */
    @Test
    void testProcessCard2() throws Exception {
        // Arrange
        when(visaCardService.getVisaDetail((CardDetail) any())).thenReturn(null);

        // Act and Assert
        assertThrows(Exception.class, () -> bankServiceImp.processCard(new CardDetail("42", "Name", "CCV", "Card Expiry")));
        verify(visaCardService).getVisaDetail((CardDetail) any());
    }


}

