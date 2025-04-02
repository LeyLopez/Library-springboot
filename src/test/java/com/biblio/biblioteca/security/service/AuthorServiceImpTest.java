package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.AuthorDTO;
import com.biblio.biblioteca.dto.AuthorMapper;
import com.biblio.biblioteca.entity.Author;
import com.biblio.biblioteca.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImpTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImp authorService;

    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        author.setLastname("Smith");
        author.setDateOfBirth(new Date(1998, 1, 1));

        authorDTO = new AuthorDTO(1L, "John Doe", "Smith", new Date(1998, 1, 1));

    }

    @Test
    void findById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDTO);

        Optional<AuthorDTO> result = authorService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(authorDTO, result.get());
    }

    @Test
    void findByName() {
        when(authorRepository.findByName("Gabriel")).thenReturn(Optional.of(author));
        when(authorMapper.toDto(author)).thenReturn(authorDTO);

        Optional<AuthorDTO> result = authorService.findByName("Gabriel");

        assertTrue(result.isPresent());
        assertEquals(authorDTO, result.get());
    }

    @Test
    void save() {
        when(authorMapper.toEntity(authorDTO)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(author);
        when(authorMapper.toDto(author)).thenReturn(authorDTO);

        AuthorDTO result = authorService.save(authorDTO);

        assertNotNull(result);
        assertEquals(authorDTO, result);
    }

    @Test
    void delete() {
        doNothing().when(authorRepository).deleteById(1L);

        authorService.delete(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        updatedAuthor.setName("Gabriel");
        updatedAuthor.setLastname("Smith");
        updatedAuthor.setDateOfBirth(new Date(1927, 3, 6));
        AuthorDTO updatedAuthorDTO = new AuthorDTO(1L, "Gabriel", "Márquez", new Date(1927, 3, 6));

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);
        when(authorMapper.toDto(updatedAuthor)).thenReturn(updatedAuthorDTO);

        Optional<AuthorDTO> result = authorService.update(1L, updatedAuthorDTO);

        assertTrue(result.isPresent());
        assertEquals(updatedAuthorDTO, result.get());
    }

    @Test
    void findAll() {
        List<Author> authors = List.of(author);
        List<AuthorDTO> authorDTOs = List.of(authorDTO);

        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.toDto(author)).thenReturn(authorDTO);

        List<AuthorDTO> result = authorService.findAll();

        assertEquals(1, result.size());
        assertEquals(authorDTO, result.get(0));
    }

    @Test
    void findAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author result = authorService.findAuthorById(1L);

        assertNotNull(result);
        assertEquals(author, result);
    }
}