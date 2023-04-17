package com.cs490.shoppingCart.ProductManagementModule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private String description;
   @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Product> products = new ArrayList<>();
}
