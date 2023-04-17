package com.cs490.shoppingCart.PaymentModule.service;

import com.cs490.shoppingCart.PaymentModule.DTO.BankResponse;
import com.cs490.shoppingCart.PaymentModule.DTO.CardDetail;

public interface BankService {
    BankResponse processCard(CardDetail cardDetail) throws Exception;
}
