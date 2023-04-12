package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetailDTO;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;

public interface MasterCardService {
    MasterCard getMasterDetail(CardDetailDTO cardDetail);
}
