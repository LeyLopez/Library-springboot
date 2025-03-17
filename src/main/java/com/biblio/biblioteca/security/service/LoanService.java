package com.biblio.biblioteca.security.service;



import com.biblio.biblioteca.dto.LoanDTO;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    Optional<LoanDTO> findById(Long id);

    LoanDTO save(LoanDTO loanDTO);

    void delete(Long id);

    Optional<LoanDTO> update(Long id, LoanDTO loanDTO);

    List<LoanDTO> findAll();

    List<LoanDTO> findByUser(Long id);
}
