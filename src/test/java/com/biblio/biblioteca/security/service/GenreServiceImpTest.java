package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.GenreDTO;
import com.biblio.biblioteca.dto.GenreMapper;
import com.biblio.biblioteca.entity.Genre;
import com.biblio.biblioteca.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImpTest {
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @InjectMocks
    private GenreServiceImp genreService;

    private Genre genre;
    private GenreDTO genreDTO;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setId(1L);
        genre.setName("Realismo Mágico");

        genreDTO = new GenreDTO(1L, "Realismo Mágico");
    }

    @Test
    void findById() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreMapper.toDTO(genre)).thenReturn(genreDTO);

        Optional<GenreDTO> result = genreService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Realismo Mágico", result.get().name());
        verify(genreRepository, times(1)).findById(1L);
    }

    @Test
    void findByName() {
        when(genreRepository.findByName("Realismo Mágico")).thenReturn(Optional.of(genre));
        when(genreMapper.toDTO(genre)).thenReturn(genreDTO);

        Optional<GenreDTO> result = genreService.findByName("Realismo Mágico");

        assertTrue(result.isPresent());
        assertEquals("Realismo Mágico", result.get().name());
        verify(genreRepository, times(1)).findByName("Realismo Mágico");
    }

    @Test
    void save() {
        when(genreMapper.toEntity(genreDTO)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.toDTO(genre)).thenReturn(genreDTO);

        GenreDTO result = genreService.save(genreDTO);

        assertNotNull(result);
        assertEquals("Realismo Mágico", result.name());
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void delete() {
        doNothing().when(genreRepository).deleteById(1L);
        genreService.delete(1L);
        verify(genreRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(genreRepository.save(genre)).thenReturn(genre);
        when(genreMapper.toDTO(genre)).thenReturn(genreDTO);

        Optional<GenreDTO> result = genreService.update(1L, genreDTO);

        assertTrue(result.isPresent());
        assertEquals("Realismo Mágico", result.get().name());
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void findAll() {
        when(genreRepository.findAll()).thenReturn(List.of(genre));
        when(genreMapper.toDTO(genre)).thenReturn(genreDTO);

        List<GenreDTO> result = genreService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void findGenreById() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        Genre result = genreService.findGenreById(1L);

        assertNotNull(result);
        assertEquals("Realismo Mágico", result.getName());
        verify(genreRepository, times(1)).findById(1L);
    }
}