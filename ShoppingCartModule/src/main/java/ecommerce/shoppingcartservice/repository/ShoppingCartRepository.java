package ecommerce.shoppingcartservice.repository;

import ecommerce.shoppingcartservice.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

    ShoppingCart findShoppingCartByUserIdEqualsAndCartStatusEquals(Long id, boolean status);
    ShoppingCart findShoppingCartByUserId(Long id);

    Optional<ShoppingCart> findShoppingCartByCartLinesId(Long id);
}
