package com.cs490.shoppingCart.PaymentModule.repository;

import com.cs490.shoppingCart.PaymentModule.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
