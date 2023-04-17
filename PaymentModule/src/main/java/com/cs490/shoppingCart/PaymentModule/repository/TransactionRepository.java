package com.cs490.shoppingCart.PaymentModule.repository;

import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findTransactionsByUserId(Integer id);
    Transaction findTransactionByOrderId(Integer id);
    Transaction findTransactionsByCardNumberOrderByTransactionDate(String cardNumber);
}
