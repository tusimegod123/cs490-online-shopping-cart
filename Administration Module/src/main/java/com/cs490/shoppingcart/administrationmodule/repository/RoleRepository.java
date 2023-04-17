package com.cs490.shoppingcart.administrationmodule.repository;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
