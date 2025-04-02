package com.biblio.biblioteca.api;

import com.biblio.biblioteca.dto.ReservationDTO;
import com.biblio.biblioteca.entity.Status;
import com.biblio.biblioteca.security.service.ReservationService;
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
class ReservationAPITest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReservationDTO reservation;

    @BeforeEach
    void setUp() {
        reservation = new ReservationDTO(1L, new Date(), new Date(), new Date(), 1L, 1L, Status.ENTREGADO);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getReservations() throws Exception {
        Mockito.when(reservationService.findAll()).thenReturn(Collections.singletonList(reservation));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reserva")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getReservationById() throws Exception {
        Mockito.when(reservationService.findById(anyLong())).thenReturn(Optional.of(reservation));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reserva/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createdReservation() throws Exception {
        Mockito.when(reservationService.save(any(ReservationDTO.class))).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reserva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateReservation() throws Exception {
        Mockito.when(reservationService.update(anyLong(), any(ReservationDTO.class))).thenReturn(Optional.of(reservation));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/reserva/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteReservation() throws Exception {
        Mockito.when(reservationService.findById(anyLong())).thenReturn(Optional.of(reservation));
        Mockito.doNothing().when(reservationService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reserva/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getReservationsByUserId() throws Exception {
        Mockito.when(reservationService.findByUserId(anyLong())).thenReturn(Collections.singletonList(reservation));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reserva/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}