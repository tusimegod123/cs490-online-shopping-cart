package ecommerce.shoppingcartservice.repository;

import ecommerce.shoppingcartservice.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {

    ShoppingCart findShoppingCartByAccountIdEqualsAndStatusEquals(Integer id, boolean status);
    ShoppingCart findShoppingCartByAccountIdEquals(Integer id);
}