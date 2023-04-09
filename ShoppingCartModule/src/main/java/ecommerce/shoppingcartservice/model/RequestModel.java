package ecommerce.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestModel {

    private Integer accountId;
    private Integer productId;
    private Integer quantity;

}
