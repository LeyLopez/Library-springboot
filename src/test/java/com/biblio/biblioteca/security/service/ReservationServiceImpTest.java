package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.ReservationDTO;
import com.biblio.biblioteca.dto.ReservationMapper;
import com.biblio.biblioteca.entity.Book;
import com.biblio.biblioteca.entity.Reservation;
import com.biblio.biblioteca.entity.Status;
import com.biblio.biblioteca.entity.User;
import com.biblio.biblioteca.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImpTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private ReservationServiceImp reservationService;

    private Reservation reservation;
    private ReservationDTO reservationDTO;
    private User user;
    private Book book;
    private Date reservationDate;
    private Date reservationEndDate;
    private Date statusChangeDate;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        book = new Book();
        book.setId(1L);

        reservationDate = new Date();
        reservationEndDate = new Date(System.currentTimeMillis() + 604800000L); // +7 días
        statusChangeDate = new Date();

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setReservationDate(reservationDate);
        reservation.setReservationEndDate(reservationEndDate);
        reservation.setStatus(Status.FINALIZADO);
        reservation.setStatusChangeDate(statusChangeDate);
        reservation.setUser(user);
        reservation.setBook(book);

        reservationDTO = new ReservationDTO(1L, reservationDate, reservationEndDate, statusChangeDate, 1L, 1L, Status.FINALIZADO);
    }

    @Test
    void findById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationMapper.toDTO(reservation)).thenReturn(reservationDTO);

        Optional<ReservationDTO> result = reservationService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        assertEquals(1L, result.get().id());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        when(reservationMapper.toEntity(reservationDTO, userService, bookService)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toDTO(reservation)).thenReturn(reservationDTO);

        ReservationDTO result = reservationService.save(reservationDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(1L, result.id());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void delete() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.delete(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationMapper.toDTO(reservation)).thenReturn(reservationDTO);

        Optional<ReservationDTO> result = reservationService.update(1L, reservationDTO);

        assertTrue(result.isPresent());
        assertEquals(Status.FINALIZADO, result.get().status());
        assertEquals(1L, result.get().id());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void findAll() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(reservationMapper.toDTO(reservation)).thenReturn(reservationDTO);

        List<ReservationDTO> result = reservationService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void findByUserId() {
        when(userService.findUserById(1L)).thenReturn(user);
        when(reservationMapper.toDTO(reservation)).thenReturn(reservationDTO);
        user.setReservations(Set.of(reservation));

        List<ReservationDTO> result = reservationService.findByUserId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        verify(userService, times(1)).findUserById(1L);
    }
}