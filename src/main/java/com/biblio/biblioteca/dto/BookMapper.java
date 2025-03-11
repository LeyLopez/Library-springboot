package com.biblio.biblioteca.dto;

import com.biblio.biblioteca.entity.Author;
import com.biblio.biblioteca.entity.Book;
import com.biblio.biblioteca.entity.Genre;
import com.biblio.biblioteca.security.service.AuthorService;
import com.biblio.biblioteca.security.service.GenreService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "genre.id", target = "genre")
    @Mapping(source = "author.id", target = "author")
    BookDTO toDTO(Book book);

    @Mapping(source = "genre.id", target = "genre")
    @Mapping(source = "author.id", target="author")
    @Mapping(target = "id", ignore = true)
    BookDTO toDTOWithoutId(Book book);

    @Mapping(source = "genre", target = "genre", qualifiedByName = "IdToGenre")
    @Mapping(source = "author", target = "author", qualifiedByName = "IdToAuthor")
    Book toEntity(BookDTO bookDTO, @Context AuthorService authorService, @Context GenreService genreService);

    @Named("IdToAuthor")
    default Author mapIdToAuthor(Long id, @Context AuthorService authorService) {
        return id != null ?authorService.findAuthorById(id) : null;
    }

    @Named("IdToGenre")
    default Genre mapIdToGenre(Long id, @Context GenreService genreService) {
        return id != null ? genreService.findGenreById(id) : null;
    }
}
