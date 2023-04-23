package com.cs490.shoppingcart.administrationmodule.repository;

import com.cs490.shoppingcart.administrationmodule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User>findUserByName(String name);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    ResponseEntity<?> deleteUsersByUserId(Optional<User> userToBeDeleted);
}
