package com.biblio.biblioteca.api;

import com.biblio.biblioteca.dto.JwtResponse;
import com.biblio.biblioteca.dto.LoginRequest;
import com.biblio.biblioteca.dto.SignupRequest;
import com.biblio.biblioteca.entity.ERole;
import com.biblio.biblioteca.entity.Role;
import com.biblio.biblioteca.entity.User;
import com.biblio.biblioteca.repository.RoleRepository;
import com.biblio.biblioteca.repository.UserRepository;
import com.biblio.biblioteca.security.jwt.JwtUtil;
import com.biblio.biblioteca.security.service.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth/")
@CrossOrigin("https://library-frontend-rho-liard.vercel.app/")
public class AuthenticationAPI {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(),
                        loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtil.generateJwtToken(authentication);
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream().map(role->role.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwtToken, "Bearer", userDetails.getUsername(), roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.username());
        user.setPassword(passwordEncoder.encode(signupRequest.password()));
        user.setEmail(signupRequest.email());
        user.setName(signupRequest.name());
        user.setLastname(signupRequest.lastname());
        user.setKindOfDocument(signupRequest.kindOfDocument());
        user.setDocumentNumber(signupRequest.documentNumber());
        user.setPhoneNumber(signupRequest.phoneNumber());
        user.setAddress(signupRequest.address());
        user.setDateOfBirth(signupRequest.dateOfBirth());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.valueOf("ROLE_USER")).orElse(null));
        user.setRoles(roles);
        User newUser = userRepository.save(user);

        return ResponseEntity.ok(newUser);
    }


}
