package com.cs490.shoppingCart.ProductManagementModule.repository;

import com.cs490.shoppingCart.ProductManagementModule.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);

    List<Product> findAllByVerified(@Param("verified")boolean verified);

    @Query(value = "select * from product p where p.product_name LIKE %:productName%", nativeQuery = true)
    List<Product> findProductByProductName(@Param("productName") String productName);

    List<Product> findProductByCategoryId(Long categoryId);

    List<Product> findProductByUserId(Long userId);

}