package com.cs490.shoppingCart.OrderProcessingModule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderline")
public class OrderLine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;
    @Column(length = 65500)
    private String productInfo;

    public OrderLine(String productInfo, Integer quanitiy, Double price){

        this.productInfo = productInfo;
        this.quantity = quantity;
        this.price = price;
    }
}
