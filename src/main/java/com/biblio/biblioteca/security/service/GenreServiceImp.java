package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.GenreDTO;
import com.biblio.biblioteca.dto.GenreMapper;
import com.biblio.biblioteca.entity.Genre;
import com.biblio.biblioteca.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreServiceImp implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreServiceImp(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }


    @Override
    public Optional<GenreDTO> findById(Long id) {
        return genreRepository.findById(id).map(genreMapper::toDTO);
    }

    @Override
    public Optional<GenreDTO> findByName(String name) {
        return genreRepository.findByName(name).map(genreMapper::toDTO);
    }

    @Override
    public GenreDTO save(GenreDTO genreDTO) {
        Genre genre = genreRepository.save(genreMapper.toEntity(genreDTO));
        return genreMapper.toDTO(genre);
    }

    @Override
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Optional<GenreDTO> update(Long id, GenreDTO genreDTO) {
        return genreRepository.findById(id).map(generoInBD-> {
                    generoInBD.setName(genreDTO.name());

                    return genreRepository.save(generoInBD);
                }
        ).map(genreMapper::toDTO);
    }

    @Override
    public List<GenreDTO> findAll() {
        return genreRepository.findAll().stream()
                .map(dto-> genreMapper.toDTO(dto))
                .collect(Collectors.toList());
    }

    @Override
    public Genre findGenreById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }
}
