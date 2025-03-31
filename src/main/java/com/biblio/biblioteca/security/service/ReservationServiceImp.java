package com.biblio.biblioteca.security.service;

import com.biblio.biblioteca.dto.ReservationDTO;
import com.biblio.biblioteca.dto.ReservationMapper;
import com.biblio.biblioteca.entity.Reservation;
import com.biblio.biblioteca.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImp implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserService userService;
    private final BookService bookService;

    public ReservationServiceImp(ReservationRepository reservationRepository, ReservationMapper reservationMapper, UserService userService, BookService bookService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public Optional<ReservationDTO> findById(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::toDTO);
    }


    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.save(reservationMapper.toEntity(reservationDTO, userService, bookService));
        reservation.changeDisponibility();
        return reservationMapper.toDTO(reservation);
    }

    @Override
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public Optional<ReservationDTO> update(Long id, ReservationDTO reservationDTO) {
        return reservationRepository.findById(id).map(reservaInBD->{
            reservaInBD.setReservationDate(reservationDTO.reservationDate());
            reservaInBD.setReservationEndDate(reservationDTO.reservationEndDate());
            reservaInBD.setStatus(reservationDTO.status());
            reservaInBD.setStatusChangeDate(reservationDTO.statusChangeDate());

            return reservationRepository.save(reservaInBD);
        }).map(reservationMapper::toDTO);
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(dto-> reservationMapper.toDTO(dto))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> findByUserId(Long id) {
        return userService.findUserById(id).getReservations()
                .stream().map(reservationMapper::toDTO).collect(Collectors.toList());
    }
}
