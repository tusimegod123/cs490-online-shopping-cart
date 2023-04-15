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
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private Product product;
    private Integer quantity;
    private Double price;

    public OrderLine(Product product, Integer quanitiy, Double price){

        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
