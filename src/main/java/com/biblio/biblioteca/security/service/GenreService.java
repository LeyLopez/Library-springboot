package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.GenreDTO;
import com.biblio.biblioteca.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<GenreDTO> findById(Long ig);

    Optional<GenreDTO> findByName(String name);

    GenreDTO save(GenreDTO genreDTO);

    void delete(Long id);

    Optional<GenreDTO> update(Long id, GenreDTO genreDTO);

    List<GenreDTO> findAll();

    Genre findGenreById(Long id);


}
