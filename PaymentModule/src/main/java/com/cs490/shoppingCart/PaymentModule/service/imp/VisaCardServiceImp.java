package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import com.cs490.shoppingCart.PaymentModule.repository.VisaCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.VisaCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class VisaCardServiceImp implements VisaCardService {

    @Autowired
    private VisaCardRepository visaCardRepository;

    @Override
    public VisaCard getVisaDetail(CardDetail cardDetail) {

        VisaCard card = visaCardRepository.getVisaCardByCardNumberAndNameAndCCV(
                cardDetail.getCardNumber(),
                cardDetail.getName(),
                cardDetail.getCCV()
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
        String formattedDate = card.getCardExpiry().format(formatter);

        if(cardDetail.getCardExpiry().equals(formattedDate)){
            return card;
        }

        return card;
    }
}
