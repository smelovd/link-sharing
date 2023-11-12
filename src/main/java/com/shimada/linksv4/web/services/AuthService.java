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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialException;

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
    public ResponseEntity<?> register(Register register) throws CredentialException {
        validateUsernameAndEmail(register.getUsername(), register.getEmail());
        User user = new User(register, passwordEncoder.encode(register.getPassword()));
        user.getRoles().add(roleRepository.findById(1L).orElse(new Role(ROLE_USER)));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Transactional
    public void validateUsernameAndEmail(String username, String email) throws CredentialException, AuthenticationException {
        if (userRepository.existsUserByEmail(email)) {
            throw new CredentialException("email already use!");
        }
        if (userRepository.existsUserByUsername(username)) {
            throw new CredentialException("username already use!");
        }
    }


    @Transactional
    public ResponseEntity<?> login(Login login) throws UsernameNotFoundException {
        User user = findUserByUsername(login.getUsername());

        authentication.authenticate(new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword()));

        JwtResponse jwtResponse = new JwtResponse(user, jwtTokenProvider.createAccessToken(user));
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @Transactional
    public User findUserByUsername(String username) {
        var user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user.get();
    }

    @Transactional
    public User findById(Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user.get();
    }
}
