package com.cs490.shoppingCart.ProductManagementModule.dto;

import lombok.Data;

@Data
public class ListProductResponseSpecificID {

    private Long productId;
    private Double price;
    private Double itemCost;

    public ListProductResponseSpecificID(Long productId, Double price, Double itemCost) {
        this.productId = productId;
        this.price = price;
        this.itemCost = itemCost;
    }

    public ListProductResponseSpecificID() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {
        this.itemCost = itemCost;
    }
}
