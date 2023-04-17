package ecommerce.shoppingcartservice.repository;

import ecommerce.shoppingcartservice.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {

    ShoppingCart findShoppingCartByUserIdEqualsAndCartStatusEquals(Integer id, boolean status);
    ShoppingCart findShoppingCartByUserId(Integer id);

    Optional<ShoppingCart> findShoppingCartByCartLinesId(int id);
}
