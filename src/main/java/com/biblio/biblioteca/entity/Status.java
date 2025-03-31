package com.biblio.biblioteca.entity;
//Aqui utilizamos enumeraciones para representar los estados que puede tomar tanto los prestamos como las reservaciones.
public enum Status {
    RESERVADO,
    PRESTADO,
    ENTREGADO,
    FINALIZADO,
    CANCELADO,
    VENCIDO
}
