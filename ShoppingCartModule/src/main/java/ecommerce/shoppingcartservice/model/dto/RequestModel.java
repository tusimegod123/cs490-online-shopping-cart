package ecommerce.shoppingcartservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestModel {

    private Long userId;
    private ProductDTO productDTO;
    private Integer quantity;

}
