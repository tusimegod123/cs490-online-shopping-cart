package com.cs490.shoppingCart.OrderProcessingModule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String category;

    public Product(String name,String description,String category,Double price){
        this.category = category;
        this.price = price;
        this.description = description;
        this.name =  name;
    }

}
