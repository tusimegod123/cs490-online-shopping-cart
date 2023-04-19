package com.cs490.shoppingCart.ProfitSharingModule.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private String orderStatus;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private Set<OrderLineDTO> orderLines;
    private String userInfo;
}