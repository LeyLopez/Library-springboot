package com.biblio.biblioteca.api;

import com.biblio.biblioteca.dto.GenreDTO;
import com.biblio.biblioteca.security.service.GenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GenreAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    private GenreDTO genre;

    @BeforeEach
    void setUp() {
        genre = new GenreDTO(1L, "Ficción");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getGenres() throws Exception {
        Mockito.when(genreService.findAll()).thenReturn(Collections.singletonList(genre));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/genero")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Ficción"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getGenreById() throws Exception {
        Mockito.when(genreService.findById(anyLong())).thenReturn(Optional.of(genre));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/genero/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ficción"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createdGenre() throws Exception {
        Mockito.when(genreService.save(any(GenreDTO.class))).thenReturn(genre);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/genero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ficción"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void actualizarGenero() throws Exception {
        Mockito.when(genreService.update(anyLong(), any(GenreDTO.class))).thenReturn(Optional.of(genre));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/genero/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ficción"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void eliminarGenero() throws Exception {
        Mockito.when(genreService.findById(anyLong())).thenReturn(Optional.of(genre));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/genero/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ficción"));
    }
}