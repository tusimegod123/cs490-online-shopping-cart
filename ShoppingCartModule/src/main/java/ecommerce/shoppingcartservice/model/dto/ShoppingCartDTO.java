package ecommerce.shoppingcartservice.model.dto;

import ecommerce.shoppingcartservice.model.CartLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShoppingCartDTO {

    private Long id;
    private Long userId;
    private Double totalPrice;
    private Set<CartLine> cartLines;
}
