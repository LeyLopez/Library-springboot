package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.LoanDTO;
import com.biblio.biblioteca.dto.LoanMapper;
import com.biblio.biblioteca.entity.Book;
import com.biblio.biblioteca.entity.Loan;
import com.biblio.biblioteca.entity.Status;
import com.biblio.biblioteca.entity.User;
import com.biblio.biblioteca.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.crypto.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImpTest {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private LoanServiceImp loanService;

    private Loan loan;
    private LoanDTO loanDTO;
    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        book = new Book();
        book.setId(1L);

        loan = new Loan();
        loan.setId(1L);
        loan.setLoanDate(new Date(2025, 03, 21));
        loan.setDevolutionDate(new Date(2025, 07, 21));
        loan.setStatus(Status.ENTREGADO);
        loan.setStatusChangeDate(new Date(2025, 07, 21));
        loan.setUser(user);
        loan.setBook(book);

        loanDTO = new LoanDTO(1L, new Date(2025, 03, 21), new Date(2025, 07, 21), new Date(2025, 07, 21), 1L, 1L, Status.ENTREGADO);
    }

    @Test
    void findById() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanMapper.toDTO(loan)).thenReturn(loanDTO);

        Optional<LoanDTO> result = loanService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        assertEquals(1L, result.get().id());
        verify(loanRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        when(loanMapper.toEntity(loanDTO, userService, bookService)).thenReturn(loan);
        when(loanRepository.save(loan)).thenReturn(loan);
        when(loanMapper.toDTO(loan)).thenReturn(loanDTO);

        LoanDTO result = loanService.save(loanDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(1L, result.id());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void delete() {
        doNothing().when(loanRepository).deleteById(1L);

        loanService.delete(1L);

        verify(loanRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(loanMapper.toDTO(loan)).thenReturn(loanDTO);

        Optional<LoanDTO> result = loanService.update(1L, loanDTO);

        assertTrue(result.isPresent());
        assertEquals(Status.ENTREGADO, result.get().status());
        assertEquals(1L, result.get().id());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void findAll() {
        when(loanRepository.findAll()).thenReturn(List.of(loan));
        when(loanMapper.toDTO(loan)).thenReturn(loanDTO);

        List<LoanDTO> result = loanService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void findByUserId() {
        when(userService.findUserById(1L)).thenReturn(user);
        when(loanMapper.toDTO(loan)).thenReturn(loanDTO);
        user.setLoans(Set.of(loan));

        List<LoanDTO> result = loanService.findByUser(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        verify(userService, times(1)).findUserById(1L);
    }
}