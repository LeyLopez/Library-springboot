package com.biblio.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

//Clase que representa a la entidad libro, aquí se definen los atributos de la tabla libros,
// entre los cuales se encuentran el titulo, la descripcion, la fecha de publicación, la cantidad de ejemplares en la biblioteca,
//la URL de la portada del libro. Además, se presentan las relaciones con las tabla autores, reservaciones,
//prestamos y generos.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "libros")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date dateOfPublication;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String coverPage;

    //Relación entre los libros y el autor
    @ManyToOne(targetEntity = Author.class)
    private Author author;

    @ManyToOne(targetEntity = Genre.class)
    private Genre genre;

    //Relacion entre el libro y sus reservas
    @JsonIgnore
    @OneToMany(targetEntity = Reservation.class, mappedBy = "book", fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    @JsonIgnore
    @OneToMany(targetEntity = Loan.class, mappedBy = "book", fetch = FetchType.LAZY)
    private Set<Loan> loans;


}
