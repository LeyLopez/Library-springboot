package com.biblio.biblioteca.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//Clase que representa a la entidad préstamo, aquí se definen los atributos de la tabla prestamos,
// entre los cuales se encuentran la fecha de prestamo, la fecha de devolución, la fecha de cambio de estado,
//. Además, se presentan las relaciones con las tabla usuarios, libros,
//y estados.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prestamos")
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loanDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date devolutionDate;


    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date statusChangeDate;


    @ManyToOne(targetEntity = User.class)
    private User user;


    @ManyToOne(targetEntity = Book.class)
    private Book book;


    @Enumerated(EnumType.STRING)
    private Status status;

    public void changeQuantity(){
        this.book.setQuantity(this.book.getQuantity() - 1);
    }

}
