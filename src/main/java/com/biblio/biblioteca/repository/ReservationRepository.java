package com.biblio.biblioteca.repository;

import com.biblio.biblioteca.dto.ReservationDTO;
import com.biblio.biblioteca.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
}
