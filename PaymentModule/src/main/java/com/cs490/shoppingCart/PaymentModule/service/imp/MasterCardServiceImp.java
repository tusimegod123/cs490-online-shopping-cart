package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.repository.MasterCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.MasterCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class MasterCardServiceImp implements MasterCardService {

    @Autowired
    private MasterCardRepository masterCardRepository;

    @Override
    public MasterCard getMasterDetail(CardDetail cardDetail) {
        MasterCard card = masterCardRepository.getMasterCardByCardNumberAndNameAndCCV(
                cardDetail.getCardNumber(),
                cardDetail.getName(),
                cardDetail.getCCV()
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
        String formattedDate = card.getCardExpiry().format(formatter);

        if(cardDetail.getCardExpiry().equals(formattedDate)){
            return card;
        }

        return null;
    }
}
