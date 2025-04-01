package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.entity.Loan;
import com.biblio.biblioteca.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanRepositoryTest {
    @Mock
    private LoanRepository loanRepository;

    @Test
    void testFindByUserId() {
        // Arrange: Simular el comportamiento del repositorio
        User user = new User();
        user.setId(1L);
        Loan loan1 = new Loan();
        loan1.setId(100L);
        loan1.setUser(user);

        Loan loan2 = new Loan();
        loan2.setId(101L);
        loan2.setUser(user);

        List<Loan> mockLoans = Arrays.asList(loan1, loan2);
        when(loanRepository.findByUserId(user.getId())).thenReturn(mockLoans);

        // Act: Llamar al método del repositorio simulado
        List<Loan> result = loanRepository.findByUserId(user.getId());

        // Assert: Verificar que el resultado es el esperado
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUser()).isEqualTo(user);
        assertThat(result.get(1).getUser()).isEqualTo(user);

        // Verificar que el método fue llamado una vez
        verify(loanRepository, times(1)).findByUserId(user.getId());
    }

}