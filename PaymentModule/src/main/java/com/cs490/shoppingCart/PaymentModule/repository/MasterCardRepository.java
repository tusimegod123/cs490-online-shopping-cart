package com.cs490.shoppingCart.PaymentModule.repository;

import com.cs490.shoppingCart.PaymentModule.model.MasterCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface MasterCardRepository extends JpaRepository<MasterCard,Integer>  {
    MasterCard getMasterCardByCardNumberAndNameAndCCVAndCardExpiry(
            String cardNumber,
            String name,
            String CCV,
            Date cardExpiry
    );
}
