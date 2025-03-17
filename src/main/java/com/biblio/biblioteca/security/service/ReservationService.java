package com.biblio.biblioteca.security.service;


import com.biblio.biblioteca.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Optional<ReservationDTO> findById(Long id);

    ReservationDTO save(ReservationDTO reservationDTO);

    void delete(Long id);

    Optional<ReservationDTO> update(Long id, ReservationDTO reservationDTO);

    List<ReservationDTO> findAll();

    List<ReservationDTO> findByUserId(Long id);
}
