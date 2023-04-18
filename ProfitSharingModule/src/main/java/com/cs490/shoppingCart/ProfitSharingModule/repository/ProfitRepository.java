package com.cs490.shoppingCart.ProfitSharingModule.repository;

import com.cs490.shoppingCart.ProfitSharingModule.model.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long> {


//    List<Product> findAllByVerified(@Param("verified")boolean verified);

}
