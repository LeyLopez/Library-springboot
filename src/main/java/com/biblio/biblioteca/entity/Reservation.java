package com.biblio.biblioteca.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//Clase que representa a la entidad reservaciones, aquí se definen los atributos de la tabla reservación,
// entre los cuales se encuentran la fecha de reservación, la fecha fin de reservación, la fecha de cambio de estado,
//. Además, se presentan las relaciones con las tabla usuarios, libros,
//y estados.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservas")
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reservationEndDate;


    @Column(nullable = false)
    private Date statusChangeDate;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Book.class)
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status;


    public void changeDisponibility (){
        this.book.setQuantity(this.book.getQuantity() - 1);
    }


}
