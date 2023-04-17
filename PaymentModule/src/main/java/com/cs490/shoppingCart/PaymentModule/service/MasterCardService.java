package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;
import com.cs490.shoppingCart.PaymentModule.model.MasterCard;

public interface MasterCardService {
    MasterCard getMasterDetail(CardDetail cardDetail);
}
