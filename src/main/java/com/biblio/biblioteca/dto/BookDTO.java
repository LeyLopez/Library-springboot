package com.biblio.biblioteca.dto;

import java.util.Date;

public record BookDTO(Long id,
                      String title,
                      String description,
                      Date dateOfPublication,
                      Integer quantity,
                      Long author,
                      String coverPage,
                      Long genre

) {
}
