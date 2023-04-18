package ecommerce.shoppingcartservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartLineRequest {

    private Long id;
    private Integer quantity;
    private Double productPrice;

}
