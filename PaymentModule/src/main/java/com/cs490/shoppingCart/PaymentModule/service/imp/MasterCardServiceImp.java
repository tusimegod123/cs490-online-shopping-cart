package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.repository.MasterCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.MasterCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterCardServiceImp implements MasterCardService {

    @Autowired
    private MasterCardRepository masterCardRepository;

    @Override
    public MasterCard getMasterDetail(CardDetail cardDetail) {
        return masterCardRepository.getMasterCardByCardNumberAndNameAndCCVAndCardExpiry(
                cardDetail.getCardNumber(),
                cardDetail.getName(),
                cardDetail.getCCV(),
                cardDetail.getCardExpiry()
        );
    }
}
