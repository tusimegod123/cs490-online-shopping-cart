package ecommerce.shoppingcartservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private Long userId;
    private Double price;
    private String imgUrl;

    public ProductDTO(String name, String description, Double price){

        this.price = price;
        this.description = description;
        this.name =  name;
    }

}
