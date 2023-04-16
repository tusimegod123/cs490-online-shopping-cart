package com.cs490.shoppingCart.OrderProcessingModule.repository;

import com.cs490.shoppingCart.OrderProcessingModule.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUserIdEquals(int userId);
}
