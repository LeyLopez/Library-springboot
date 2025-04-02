package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.entity.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorRepositoryTest {
    @Mock
    private AuthorRepository authorRepository;

    @Test
    void findByName() {
        // Arrange: Simular el comportamiento del repositorio
        String authorName = "Gabriel García Márquez";
        Author author = new Author();
        author.setId(1L);
        author.setName(authorName);

        when(authorRepository.findByName(authorName)).thenReturn(Optional.of(author));

        // Act: Llamar al método del repositorio simulado
        Optional<Author> result = authorRepository.findByName(authorName);

        // Assert: Verificar que el resultado es el esperado
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(authorName);

        // Verificar que el método fue llamado una vez
        verify(authorRepository, times(1)).findByName(authorName);
    }
}