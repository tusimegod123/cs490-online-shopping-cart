package com.cs490.shoppingCart.PaymentModule.repository;

import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import com.cs490.shoppingCart.PaymentModule.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByUserId(Long id);
    Transaction findTransactionByOrderId(Long id);
    Transaction findFirstByCardNumberOrderByIdDesc(String cardNumber);

    Transaction findFirstByCardNumberAndAndTransactionStatusOrderByIdDesc(String cardNumber, TransactionStatus status);
}
