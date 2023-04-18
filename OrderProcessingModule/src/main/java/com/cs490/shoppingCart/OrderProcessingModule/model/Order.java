package com.cs490.shoppingCart.OrderProcessingModule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    //   Still in doubt
    private Double totalPrice;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shoppingcart_id")
    private Set<OrderLine> orderLines;
    private String userInfo;
}