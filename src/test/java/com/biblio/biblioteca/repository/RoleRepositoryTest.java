package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.entity.ERole;
import com.biblio.biblioteca.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryTest {
    @Mock
    private RoleRepository roleRepository;

    @Test
    void findByName() {
        // Arrange: Simular el comportamiento del repositorio
        ERole roleName = ERole.ROLE_ADMIN;
        Role role = new Role();
        role.setId(1L);
        role.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        // Act: Llamar al método del repositorio simulado
        Optional<Role> result = roleRepository.findByName(roleName);

        // Assert: Verificar que el resultado es el esperado
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(roleName);

        // Verificar que el método fue llamado una vez
        verify(roleRepository, times(1)).findByName(roleName);
    }
}