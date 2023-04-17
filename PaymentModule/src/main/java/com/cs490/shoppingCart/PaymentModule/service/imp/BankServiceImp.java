package com.cs490.shoppingCart.PaymentModule.service.imp;

import com.cs490.shoppingCart.PaymentModule.DTO.BankResponse;
import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.DTO.PaymentType;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
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
    public BankResponse processCard(CardDetail cardDetail) throws Exception {
        BankResponse response = new BankResponse();

        if(cardDetail.getCardNumber().charAt(0) == '5'){
            MasterCard card = masterCardService.getMasterDetail(cardDetail);
            System.out.println(card);
            if(card == null) {
                throw new Exception("The card doesn't exists in MASTER card table!");
            }

            response.setPaymentType(PaymentType.MASTER);
            response.setCurrentBalance(card.getCardValue());
        } else if(cardDetail.getCardNumber().charAt(0) == '4'){
            VisaCard card = visaCardService.getVisaDetail(cardDetail);

            if(card == null) {
                throw new Exception("The card doesn't exists in VISA card table!");
            }

            response.setPaymentType(PaymentType.VISA);
            response.setCurrentBalance(card.getCardValue());
        }

        return response;
    }


}
