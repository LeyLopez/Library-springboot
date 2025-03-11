package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.BookDTO;
import com.biblio.biblioteca.dto.BookMapper;
import com.biblio.biblioteca.entity.Book;
import com.biblio.biblioteca.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImp(BookRepository bookRepository, BookMapper bookMapper, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorService = authorService;
        this.genreService = genreService;
    }


    @Override
    public Optional<BookDTO> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO);
    }

    @Override
    public Optional<BookDTO> findByName(String titulo) {
        return bookRepository.findByTitle(titulo)
                .map(bookMapper::toDTO);
    }

    @Override
    public BookDTO save(BookDTO bookDTO) {
        Book book = bookRepository.save(bookMapper.toEntity(bookDTO, authorService, genreService));
        return bookMapper.toDTO(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<BookDTO> update(Long id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(libroInBD->{
                        libroInBD.setTitle(bookDTO.title());
                        libroInBD.setDescription(bookDTO.description());
                        libroInBD.setDateOfPublication(bookDTO.dateOfPublication());
                        libroInBD.setQuantity(bookDTO.quantity());
                        libroInBD.setCoverPage(bookDTO.coverPage());

                        return bookRepository.save(libroInBD);
                    }
                ).map(bookMapper::toDTO);
    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(dto-> bookMapper.toDTO(dto))
                .collect(Collectors.toList());
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

}
