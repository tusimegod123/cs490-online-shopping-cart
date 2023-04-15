package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetailDTO;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;

public interface VisaCardService {
    VisaCard getVisaDetail(CardDetailDTO cardDetail);
}
