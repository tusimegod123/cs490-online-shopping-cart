package ecommerce.shoppingcartservice.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductDTO {

    private Integer productId;
    private String productName;
    private String description;
    private Long userId;
    private Double price;
    private String imageUrl;

    public ProductDTO(String name, String description, Double price){

        this.price = price;
        this.description = description;
        this.productName =  productName;
    }

}
