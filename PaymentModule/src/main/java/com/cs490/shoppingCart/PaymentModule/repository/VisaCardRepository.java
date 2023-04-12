package com.cs490.shoppingCart.PaymentModule.repository;

import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface VisaCardRepository extends JpaRepository<VisaCard, Integer> {
    VisaCard getVisaCardByCardNumberAndNameAndCCVAndCardExpiry(
            String cardNumber,
            String name,
            String CCV,
            Date cardExpiry
    );
}
