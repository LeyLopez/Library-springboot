package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {
    @Mock
    private BookRepository bookRepository;

    @Test
    void findByTitle() {
        // Arrange: Simular el comportamiento del repositorio
        String bookTitle = "Cien años de soledad";
        Book book = new Book();
        book.setId(1L);
        book.setTitle(bookTitle);

        when(bookRepository.findByTitle(bookTitle)).thenReturn(Optional.of(book));

        // Act: Llamar al método del repositorio simulado
        Optional<Book> result = bookRepository.findByTitle(bookTitle);

        // Assert: Verificar que el resultado es el esperado
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo(bookTitle);

        // Verificar que el método fue llamado una vez
        verify(bookRepository, times(1)).findByTitle(bookTitle);
    }
}