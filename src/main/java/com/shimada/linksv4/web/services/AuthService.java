package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.Role;
import com.shimada.linksv4.models.User;
import com.shimada.linksv4.web.repository.RoleRepository;
import com.shimada.linksv4.web.repository.UserRepository;
import com.shimada.linksv4.requests.auth.Login;
import com.shimada.linksv4.requests.auth.Register;
import com.shimada.linksv4.responses.JwtResponse;
import com.shimada.linksv4.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.shimada.linksv4.models.ERole.ROLE_USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authentication;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<?> register(Register register) {
        if (userRepository.findUserByEmail(register.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findUserByUsername(register.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already in use", HttpStatus.BAD_REQUEST);
        }
        Set<Role> roles = new HashSet<>();
        var role = roleRepository.findByRole(ROLE_USER).orElse(null);
        if (role == null) {
            roleRepository.save(new Role(ROLE_USER));
            role = roleRepository.findById(1L).orElse(null);
            if (role == null) System.out.println("role is null, check endpoint /register");
        }
        roles.add(role);
        User user = new User(
                register.getEmail(),
                register.getUsername(),
                passwordEncoder.encode(register.getPassword()),
                roles
        );
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> login(Login login) {
        User user = userRepository.findUserByUsername(login.getUsername())
                .orElse(null);
        if (user == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        JwtResponse jwtResponse = new JwtResponse();
        authentication.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(), login.getPassword()
                ));
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getUsername(), user.getRoles()
        ));
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
