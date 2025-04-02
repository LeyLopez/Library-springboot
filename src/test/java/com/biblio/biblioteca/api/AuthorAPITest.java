package com.biblio.biblioteca.api;


import com.biblio.biblioteca.dto.AuthorDTO;
import com.biblio.biblioteca.security.service.AuthorService;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthorAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthorDTO author;

    @BeforeEach
    void setUp() {
        author = new AuthorDTO(1L, "Gabriel", "García Márquez", new Date());
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void getAuthors() throws Exception {
            Mockito.when(authorService.findAll()).thenReturn(Collections.singletonList(author));

            mockMvc.perform(MockMvcRequestBuilders.get("/api/autor")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Gabriel"))
                    .andExpect(jsonPath("$[0].lastname").value("García Márquez"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void getAuthorById() throws Exception {
        Mockito.when(authorService.findById(anyLong())).thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/autor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.lastname").value("García Márquez"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void createdAuthor() throws Exception {
        Mockito.when(authorService.save(any(AuthorDTO.class))).thenReturn(author);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.lastname").value("García Márquez"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void updateAuthor() throws Exception {

        Mockito.when(authorService.update(anyLong(), any(AuthorDTO.class))).thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/autor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.lastname").value("García Márquez"));

    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void deleteAuthor() throws Exception {
        Mockito.when(authorService.findById(anyLong())).thenReturn(Optional.of(author));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/autor/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.lastname").value("García Márquez"));

    }
}