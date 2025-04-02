package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @Test
    void findByName() {
        // Arrange
        String name = "Juan Pérez";
        User user = new User();
        user.setId(1L);
        user.setName(name);

        when(userRepository.findByName(name)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userRepository.findByName(name);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(name);
        verify(userRepository, times(1)).findByName(name);
    }

    @Test
    void findByEmail() {
        // Arrange
        String email = "juan@example.com";
        User user = new User();
        user.setId(2L);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userRepository.findByEmail(email);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByPhoneNumber() {
        // Arrange
        String phoneNumber = "123456789";
        User user = new User();
        user.setId(3L);
        user.setPhoneNumber(phoneNumber);

        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userRepository.findByPhoneNumber(phoneNumber);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getPhoneNumber()).isEqualTo(phoneNumber);
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
    }

    @Test
    void findByUsername() {
        // Arrange
        String username = "juan123";
        User user = new User();
        user.setId(4L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userRepository.findByUsername(username);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(username);
        verify(userRepository, times(1)).findByUsername(username);
    }
}