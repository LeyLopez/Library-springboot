package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.entity.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreRepositoryTest {
    @Mock
    private GenreRepository genreRepository;

    @Test
    void findByName() {
        // Arrange: Simular el comportamiento del repositorio
        String genreName = "Fantasía";
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName(genreName);

        when(genreRepository.findByName(genreName)).thenReturn(Optional.of(genre));

        // Act: Llamar al método del repositorio simulado
        Optional<Genre> result = genreRepository.findByName(genreName);

        // Assert: Verificar que el resultado es el esperado
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(genreName);

        // Verificar que el método fue llamado una vez
        verify(genreRepository, times(1)).findByName(genreName);
    }
}