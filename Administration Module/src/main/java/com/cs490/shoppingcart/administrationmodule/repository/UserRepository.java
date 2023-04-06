package com.cs490.shoppingcart.administrationmodule.repository;

import com.cs490.shoppingcart.administrationmodule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String username);
}
