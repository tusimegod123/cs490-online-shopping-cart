package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import com.cs490.shoppingCart.PaymentModule.repository.VisaCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.VisaCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisaCardServiceImp implements VisaCardService {

    @Autowired
    private VisaCardRepository visaCardRepository;

    @Override
    public VisaCard getVisaDetail(CardDetail cardDetail) {
        return  visaCardRepository.getVisaCardByCardNumberAndNameAndCCVAndCardExpiry(
                cardDetail.getCardNumber(),
                cardDetail.getName(),
                cardDetail.getCCV(),
                cardDetail.getCardExpiry()
        );
    }
}
