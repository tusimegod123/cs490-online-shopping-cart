package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;

public interface VisaCardService {
    VisaCard getVisaDetail(CardDetail cardDetail);
}
