package com.cs490.shoppingcart.administrationmodule.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RoleService.class})
@ExtendWith(SpringExtension.class)
class RoleServiceTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    /**
     * Method under test: {@link RoleService#saveRole(Role)}
     */
    @Test
    void testSaveRole() {
        // Arrange
        when(roleRepository.save((Role) any())).thenReturn(new Role("Role Name"));
        Role role = new Role("Role Name");

        // Act
        roleService.saveRole(role);

        // Assert that nothing has changed
        verify(roleRepository).save((Role) any());
        assertEquals("Role Name", role.toString());
    }


    /**
     * Method under test: {@link RoleService#findRole(Integer)}
     */
    @Test
    void testFindRole() {
        // Arrange
        Role role = new Role("Role Name");
        when(roleRepository.findById((Integer) any())).thenReturn(Optional.of(role));

        // Act and Assert
        assertSame(role, roleService.findRole(123));
        verify(roleRepository).findById((Integer) any());
    }

}

