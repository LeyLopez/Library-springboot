package com.biblio.biblioteca.api;


import com.biblio.biblioteca.dto.AuthorDTO;
import com.biblio.biblioteca.exception.NotFoundException;
import com.biblio.biblioteca.security.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/autor")
public class AuthorAPI {

    private final AuthorService authorService;


    public AuthorAPI(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAuthors(){
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id){
        return authorService.findById(id)
                .map(a->ResponseEntity.ok().body(a))
                .orElseThrow(()-> new NotFoundException("No se encontró el autor con el ID "+id));
    }


    @PostMapping
    public ResponseEntity<AuthorDTO> createdAuthor(@RequestBody AuthorDTO autor){
        return createAutor(autor);
    }

    private ResponseEntity<AuthorDTO> createAutor(AuthorDTO autor) {
        AuthorDTO newAutor = authorService.save(autor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newAutor.id()).toUri();

        return ResponseEntity.created(location).body(newAutor);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO autor){
        Optional<AuthorDTO> autorToUpdate = authorService.update(id, autor);
        return autorToUpdate.map(a->ResponseEntity.ok(a))
                .orElseGet(()-> {
                    return createAutor(autor);
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable Long id){
        return authorService.findById(id).map(a->{
            authorService.delete(id);
            return ResponseEntity.ok().body(a);
        }).orElseThrow(()->new NotFoundException("No se encontró el autor con el ID: "+id));
    }
}
