package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetailDTO;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import com.cs490.shoppingCart.PaymentModule.repository.MasterCardRepository;
import com.cs490.shoppingCart.PaymentModule.repository.VisaCardRepository;
import com.cs490.shoppingCart.PaymentModule.service.BankService;
import com.cs490.shoppingCart.PaymentModule.service.MasterCardService;
import com.cs490.shoppingCart.PaymentModule.service.VisaCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImp implements BankService {

    @Autowired
    private VisaCardService visaCardService;

    @Autowired
    private MasterCardService masterCardService;

    @Override
    public Double processCard(CardDetailDTO cardDetail) {
        Double currentBalance = 0.0;

        if(cardDetail.getCardNumber().charAt(0) == 5){
            MasterCard card = masterCardService.getMasterDetail(cardDetail);
            currentBalance = card.getCardValue();
        } else if(cardDetail.getCardNumber().charAt(0) == 4){
            VisaCard card = visaCardService.getVisaDetail(cardDetail);
            currentBalance = card.getCardValue();
        }

        return currentBalance;
    }


}
