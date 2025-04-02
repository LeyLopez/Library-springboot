package com.biblio.biblioteca.api;

import com.biblio.biblioteca.dto.UserDTO;
import com.biblio.biblioteca.security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserAPITest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO user;

    @BeforeEach
    void setUp() {
        user = new UserDTO(1L, "Juan", "Pérez", "juan@example.com", "juanperez", "password123", "DNI", 12345678, new Date(), "123456789", "Calle 123");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUsers() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserById() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createdUser() throws Exception {
        Mockito.when(userService.save(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateUser() throws Exception {
        Mockito.when(userService.update(anyLong(), any(UserDTO.class))).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUser() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserByUsername() throws Exception {
        Mockito.when(userService.findByUsername(anyString())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/usuario/username/juanperez")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("juanperez"));
    }
}