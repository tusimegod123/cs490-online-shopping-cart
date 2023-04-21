package com.cs490.shoppingCart.PaymentModule.repository;

import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import com.cs490.shoppingCart.PaymentModule.model.VisaCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface VisaCardRepository extends JpaRepository<VisaCard, Long> {
    VisaCard getVisaCardByCardNumberAndNameAndCCVAndCardExpiry(
            String cardNumber,
            String name,
            String CCV,
            LocalDate cardExpiry
    );

    VisaCard getVisaCardByCardNumber(String cardNumber);
}
