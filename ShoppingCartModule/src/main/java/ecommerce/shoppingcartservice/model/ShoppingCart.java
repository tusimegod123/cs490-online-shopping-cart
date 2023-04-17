package ecommerce.shoppingcartservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shoppingcart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Boolean cartStatus;
    private LocalDateTime cartDate;
//   Still in doubt
    private Double totalPrice;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shoppingcart_id")
    private Set<CartLine> cartLines;
}
