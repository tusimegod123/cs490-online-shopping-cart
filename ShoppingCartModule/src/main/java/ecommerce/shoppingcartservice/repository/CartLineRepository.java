package ecommerce.shoppingcartservice.repository;

import ecommerce.shoppingcartservice.model.CartLine;
import ecommerce.shoppingcartservice.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartLineRepository extends JpaRepository<CartLine,Long> {


}
