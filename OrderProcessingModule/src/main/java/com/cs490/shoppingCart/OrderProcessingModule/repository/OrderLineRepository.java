package com.cs490.shoppingCart.OrderProcessingModule.repository;

import com.cs490.shoppingCart.OrderProcessingModule.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {
}
