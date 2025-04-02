package com.biblio.biblioteca.api;

import com.biblio.biblioteca.dto.BookDTO;
import com.biblio.biblioteca.security.service.BookService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class BookAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookDTO book;

    @BeforeEach
    void setUp() {
        book = new BookDTO(1L, "Libro de prueba", "Descripción", new Date(), 10, 1L, "cover.jpg", 2L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getBooks() throws Exception {
        Mockito.when(bookService.findAll()).thenReturn(Collections.singletonList(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/libro")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Libro de prueba"))
                .andExpect(jsonPath("$[0].author").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getBookById() throws Exception {
        Mockito.when(bookService.findById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/libro/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Libro de prueba"))
                .andExpect(jsonPath("$.author").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createdBook() throws Exception {
        Mockito.when(bookService.save(any(BookDTO.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/libro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Libro de prueba"))
                .andExpect(jsonPath("$.author").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateBook() throws Exception {
        Mockito.when(bookService.update(anyLong(), any(BookDTO.class))).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/libro/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Libro de prueba"))
                .andExpect(jsonPath("$.author").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteBook() throws Exception {
        Mockito.when(bookService.findById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/libro/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Libro de prueba"))
                .andExpect(jsonPath("$.author").value(1L));
    }
}