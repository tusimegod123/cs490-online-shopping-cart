package ecommerce.shoppingcartservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartline")
public class CartLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;
    private Integer productId;
    @Column(length = 65500 )
    private String productInfo;
    @Override
    public boolean equals(Object obj){
        if(obj instanceof CartLine){
            CartLine cartLine = (CartLine) obj;
            return cartLine.productId.equals(id);
        }
        return false;
    }
    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}
