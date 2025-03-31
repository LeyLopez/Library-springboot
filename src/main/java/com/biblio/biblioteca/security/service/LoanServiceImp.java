package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.LoanDTO;
import com.biblio.biblioteca.dto.LoanMapper;
import com.biblio.biblioteca.entity.Loan;
import com.biblio.biblioteca.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImp implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final UserService userService;
    private final BookService bookService;

    public LoanServiceImp(LoanRepository loanRepository, LoanMapper loanMapper, UserService userService, BookService bookService) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.userService = userService;
        this.bookService = bookService;
    }


    @Override
    public Optional<LoanDTO> findById(Long id) {
        return loanRepository.findById(id).map(loanMapper::toDTO);
    }


    @Override
    public LoanDTO save(LoanDTO loanDTO) {
        Loan loan = loanRepository.save(loanMapper.toEntity(loanDTO, userService, bookService));
        loan.changeQuantity();
        return loanMapper.toDTO(loan);
    }

    @Override
    public void delete(Long id) {
        loanRepository.deleteById(id);
    }

    @Override
    public Optional<LoanDTO> update(Long id, LoanDTO loanDTO) {
        return loanRepository.findById(id).map(prestamoInBD->{
            prestamoInBD.setLoanDate(loanDTO.loanDate());
            prestamoInBD.setDevolutionDate(loanDTO.devolutionDate());
            prestamoInBD.setStatus(loanDTO.status());
            prestamoInBD.setStatusChangeDate(loanDTO.statusChangeDate());

            return loanRepository.save(prestamoInBD);

        }).map(loanMapper::toDTO);
    }

    @Override
    public List<LoanDTO> findAll() {
        return loanRepository.findAll().stream()
                .map(dto-> loanMapper.toDTO(dto))
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanDTO> findByUser(Long id) {
        return userService.findUserById(id).getLoans()
                .stream().map(loanMapper::toDTO).collect(Collectors.toList());
    }
}
