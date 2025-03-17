package com.biblio.biblioteca.security.service;



import com.biblio.biblioteca.dto.LoanDTO;
import com.biblio.biblioteca.dto.ReservationDTO;
import com.biblio.biblioteca.dto.UserDTO;
import com.biblio.biblioteca.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByEmail(String email);

    Optional<UserDTO> findByPhoneNumber(String phoneNumber);

    Optional<UserDTO> findByName(String name);

    UserDTO save(UserDTO userDTO);

    void delete(Long id);

    Optional<UserDTO> update(Long id, UserDTO userDTO);

    List<UserDTO> findAll();

    User findUserById(Long id);

    Optional<UserDTO> findByUsername(String username);
}
