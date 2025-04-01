package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.entity.Reservation;
import com.biblio.biblioteca.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationRepositoryTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Test
    void testFindByUserId() {
        // Arrange: Simular el comportamiento del repositorio
        User user = new User();
        user.setId(1L);
        Reservation res1 = new Reservation();
        res1.setId(100L);
        res1.setUser(user);

        Reservation res2 = new Reservation();
        res2.setId(101L);
        res2.setUser(user);

        List<Reservation> mockReservations = Arrays.asList(res1, res2);
        when(reservationRepository.findByUserId(user.getId())).thenReturn(mockReservations);

        // Act: Llamar al método del repositorio simulado
        List<Reservation> result = reservationRepository.findByUserId(user.getId());

        // Assert: Verificar que el resultado es el esperado
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUser()).isEqualTo(user);
        assertThat(result.get(1).getUser()).isEqualTo(user);

        // Verificar que el método fue llamado una vez
        verify(reservationRepository, times(1)).findByUserId(user.getId());
    }

}