package ecommerce.shoppingcartservice.dto;

import ecommerce.shoppingcartservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartLineRequest {

    private Integer id;
    private Integer productId;
    private Integer quantity;
    private Double productPrice;
}
