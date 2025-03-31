package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.UserDTO;
import com.biblio.biblioteca.dto.UserMapper;
import com.biblio.biblioteca.entity.User;
import com.biblio.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImp userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("123456789");
        user.setDateOfBirth(new Date(1990, 1, 1));
        user.setUsername("john");
        user.setPassword("password");
        user.setAddress("123 Street");
        user.setKindOfDocument("CEDULA");
        user.setDocumentNumber(2343546);

        userDTO = new UserDTO(1L, "John", "Doe", "john.doe@example.com", "jhon", "password",
                "CEDULA", 2343546, new Date(1990, 1, 1), "123456789", "123 Street");
    }

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTOWithoutId(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().name());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(userMapper.toDTOWithoutId(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.findByEmail("john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals("john.doe@example.com", result.get().email());
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void findByPhoneNumber() {
        when(userRepository.findByPhoneNumber("123456789")).thenReturn(Optional.of(user));
        when(userMapper.toDTOWithoutId(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.findByPhoneNumber("123456789");

        assertTrue(result.isPresent());
        assertEquals("123456789", result.get().phoneNumber());
        verify(userRepository, times(1)).findByPhoneNumber("123456789");
    }

    @Test
    void findByName() {
        when(userRepository.findByName("John")).thenReturn(Optional.of(user));
        when(userMapper.toDTOWithoutId(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.findByName("John");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().name());
        verify(userRepository, times(1)).findByName("John");
    }

    @Test
    void save() {
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.save(userDTO);

        assertNotNull(result);
        assertEquals("John", result.name());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void delete() {
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.update(1L, userDTO);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().name());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findUserById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.findByUsername("johndoe");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().name());
        verify(userRepository, times(1)).findByUsername("johndoe");
    }
}