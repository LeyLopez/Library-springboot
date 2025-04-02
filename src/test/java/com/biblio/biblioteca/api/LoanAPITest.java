package com.biblio.biblioteca.api;

import com.biblio.biblioteca.dto.LoanDTO;
import com.biblio.biblioteca.entity.Status;
import com.biblio.biblioteca.security.service.LoanService;
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
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoanAPITest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoanDTO loan;

    @BeforeEach
    void setUp() {
        loan = new LoanDTO(1L, new Date(), new Date(), new Date(), 1L, 1L, Status.ENTREGADO);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getLoans() throws Exception {
        Mockito.when(loanService.findAll()).thenReturn(Collections.singletonList(loan));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/prestamo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getLoanById() throws Exception {
        Mockito.when(loanService.findById(anyLong())).thenReturn(Optional.of(loan));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/prestamo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createdLoan() throws Exception {
        Mockito.when(loanService.save(any(LoanDTO.class))).thenReturn(loan);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateLoan() throws Exception {
        Mockito.when(loanService.update(anyLong(), any(LoanDTO.class))).thenReturn(Optional.of(loan));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/prestamo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteLoan() throws Exception {
        Mockito.when(loanService.findById(anyLong())).thenReturn(Optional.of(loan));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/prestamo/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getLoansUser() throws Exception {
        Mockito.when(loanService.findByUser(anyLong())).thenReturn(Collections.singletonList(loan));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/prestamo/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}