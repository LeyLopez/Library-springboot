package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.BookDTO;
import com.biblio.biblioteca.dto.BookMapper;
import com.biblio.biblioteca.entity.Author;
import com.biblio.biblioteca.entity.Book;
import com.biblio.biblioteca.entity.Genre;
import com.biblio.biblioteca.repository.BookRepository;
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
class BookServiceImpTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    @InjectMocks
    private BookServiceImp bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Cien Años de Soledad");
        book.setDescription("Novela icónica");
        book.setDateOfPublication(new Date(1967, 5, 30));
        book.setQuantity(5);
        Author author = new Author();
        author.setId(1L);
        author.setName("Gabo");
        author.setLastname("Marquez");
        author.setDateOfBirth(new Date(1998, 1, 1));
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Genre");

        book.setAuthor(author);
        book.setCoverPage("cover.jpg");

        bookDTO = new BookDTO(1L, "Cien Años de Soledad", "Novela icónica", new Date(1967, 5, 30), 5, 1L, "cover.jpg", 1L);
    }

    @Test
    void findById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        Optional<BookDTO> result = bookService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Cien Años de Soledad", result.get().title());
        verify(bookRepository, times(1)).findById(1L);

    }

    @Test
    void findByName() {
        when(bookRepository.findByTitle("Cien Años de Soledad")).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        Optional<BookDTO> result = bookService.findByName("Cien Años de Soledad");

        assertTrue(result.isPresent());
        assertEquals("Cien Años de Soledad", result.get().title());
        verify(bookRepository, times(1)).findByTitle("Cien Años de Soledad");
    }

    @Test
    void save() {
        when(bookMapper.toEntity(bookDTO, authorService, genreService)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        BookDTO result = bookService.save(bookDTO);

        assertNotNull(result);
        assertEquals("Cien Años de Soledad", result.title());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void delete() {
        doNothing().when(bookRepository).deleteById(1L);
        bookService.delete(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void update() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        Optional<BookDTO> result = bookService.update(1L, bookDTO);

        assertTrue(result.isPresent());
        assertEquals("Cien Años de Soledad", result.get().title());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void findAll() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        List<BookDTO> result = bookService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.findBookById(1L);

        assertNotNull(result);
        assertEquals("Cien Años de Soledad", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }
}