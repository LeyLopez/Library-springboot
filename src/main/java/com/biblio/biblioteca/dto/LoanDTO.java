package com.biblio.biblioteca.dto;

import com.biblio.biblioteca.entity.Status;

import java.util.Date;

public record LoanDTO(Long id,
                      Date loanDate,
                      Date devolutionDate,
                      Date statusChangeDate,
                      Long user,
                      Long book,
                      Status status) {
}   
