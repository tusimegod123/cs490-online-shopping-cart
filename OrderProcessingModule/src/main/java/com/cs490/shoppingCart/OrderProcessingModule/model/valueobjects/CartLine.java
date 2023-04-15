package com.cs490.shoppingCart.OrderProcessingModule.model.valueobjects;

import com.cs490.shoppingCart.OrderProcessingModule.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartLine {

    private Integer id;
    private Product product;
    private Integer quantity;
    private Double price;

    @Override
    public boolean equals(Object obj){
        if(obj instanceof CartLine){
            CartLine cartLine = (CartLine) obj;
            return cartLine.product.getId().equals(id);
        }
        return false;
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
