package com.biblio.biblioteca.api;



import com.biblio.biblioteca.dto.UserDTO;
import com.biblio.biblioteca.exception.NotFoundException;
import com.biblio.biblioteca.security.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "https://library-frontend-rho-liard.vercel.app/")
public class UserAPI {

    private final UserService userService;


    public UserAPI(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return userService.findById(id).map(l->ResponseEntity.ok().body(l))
                .orElseThrow(()->new NotFoundException("No se encontró el usuario con el ID "+id));
    }


    @PostMapping
    public ResponseEntity<UserDTO> createdUser(@RequestBody UserDTO usuario) {
        return createUser(usuario);
    }

    private ResponseEntity<UserDTO> createUser(UserDTO usuario) {
        UserDTO newUsuario = userService.save(usuario);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newUsuario.id()).toUri();
        return ResponseEntity.created(location).body(newUsuario);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO usuario) {
        Optional<UserDTO> usuarioToUpdate = userService.update(id, usuario);
        return usuarioToUpdate.map(l->ResponseEntity.ok(l))
                .orElseGet(()->createUser(usuario));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(l->{
                    userService.delete(id);
                    return ResponseEntity.ok().body(l);
                }).orElseThrow(()->new NotFoundException("No se encontró el usuario con el ID "+id));
    }


    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(u->ResponseEntity.ok().body(u))
                .orElseThrow(()->new NotFoundException("No se pudo encontrar el cliente con el username "+username));
    }


}
