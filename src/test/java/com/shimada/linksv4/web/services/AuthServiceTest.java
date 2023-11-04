package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.User;
import com.shimada.linksv4.requests.auth.Login;
import com.shimada.linksv4.requests.auth.Register;
import com.shimada.linksv4.security.JwtTokenProvider;
import com.shimada.linksv4.web.repository.RoleRepository;
import com.shimada.linksv4.web.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private AuthenticationManager authentication;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private RoleRepository roleRepository;

    @Test
    void register() {
        Register user = new Register();
        var statusCode = authService.register(user).getStatusCode();
        assertEquals(HttpStatus.CREATED, statusCode);
    }

    @Test
    void login() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        Login login = new Login();
        login.setUsername("username");
        login.setPassword("password");
        var response = authService.login(login);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository).findUserByUsername("username");
    }
}