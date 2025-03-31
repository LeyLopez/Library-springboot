package com.biblio.biblioteca.api;

import com.biblio.biblioteca.dto.BookDTO;
import com.biblio.biblioteca.exception.NotFoundException;
import com.biblio.biblioteca.security.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libro")
@CrossOrigin(origins = "http://localhost:5173/")
public class BookAPI {

    private final BookService bookService;

    public BookAPI(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable ("id") Long id) {
        return bookService.findById(id).map(l->ResponseEntity.ok().body(l))
                .orElseThrow(()->new NotFoundException("No se encontró el libro con el ID "+id));
    }


    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BookDTO> createdBook(@RequestBody BookDTO libro) {
        return createBook(libro);
    }

    private ResponseEntity<BookDTO> createBook(BookDTO libro) {
        BookDTO newLibro = bookService.save(libro);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newLibro.id()).toUri();
        return ResponseEntity.created(location).body(newLibro);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO book) {
        Optional<BookDTO> libroToUpdate = bookService.update(id, book);
        return libroToUpdate.map(l->ResponseEntity.ok(l))
                .orElseGet(()->createdBook(book));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable Long id) {
        return bookService.findById(id)
                .map(l->{
                    bookService.delete(id);
                    return ResponseEntity.ok().body(l);
                }).orElseThrow(()->new NotFoundException("No se encontró el libro con el ID "+id));
    }


}
