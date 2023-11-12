package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.ERole;
import com.shimada.linksv4.models.Role;
import com.shimada.linksv4.models.User;
import com.shimada.linksv4.requests.auth.Login;
import com.shimada.linksv4.requests.auth.Register;
import com.shimada.linksv4.web.controllers.AuthController;
import com.shimada.linksv4.web.repository.RoleRepository;
import com.shimada.linksv4.web.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.login.CredentialException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Before
    public void before() {
        Mockito.reset(userRepository, roleRepository);
    }

    @Test
    void registerShouldReturnUserAndStatusCreated() throws CredentialException {
        Register register = new Register("email@gmail.com", "username", "password");

        var response = authController.register(register);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void registerShouldReturnUsernameAlreadyUsedAndStatusBadRequest() throws CredentialException {
        Register register = new Register("email@gmail.com", "username", "password");
        when(userRepository.existsUserByUsername(register.getUsername())).thenReturn(true);

        var response = authController.register(register);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Problem with register! Username already use!", response.getBody());
        verify(userRepository).existsUserByUsername(register.getUsername());
    }

    @Test
    void registerShouldReturnEmailAlreadyUsedAndStatusBadRequest() throws CredentialException {
        Register register = new Register("email@gmail.com", "username", "password");
        when(userRepository.existsUserByEmail(register.getEmail())).thenReturn(true);

        var response = authController.register(register);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Problem with register! Email already use!", response.getBody());
        verify(userRepository).existsUserByEmail(register.getEmail());
    }

    @Test
    void loginShouldReturnNewAccessToken() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, ERole.ROLE_USER));
        User user = new User("email@gmail.com", "username", "$2a$10$a4Gpxd0.iP1TztY9GH9bjuKRl/eLmWH/IISkeqPhbrEti5m1NkRq.", roles);

        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(1L, ERole.ROLE_USER)));

        Login login = new Login("username", "password");

        var response1 = authController.login(login);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        System.out.println(response1.getBody().toString());
        verify(userRepository, times(2)).findUserByUsername("username");
    }

    @Test
    void loginShouldReturnUserNotFoundAndStatusBadRequest() {

        when(userRepository.findUserByUsername("username1")).thenReturn(Optional.empty());
        when(roleRepository.findByRole(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(1L, ERole.ROLE_USER)));

        Login login = new Login("username1", "password");

        var response1 = authController.login(login);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals("Problem with login! User not found!", response1.getBody());
        verify(userRepository).findUserByUsername("username1");
    }

    @Test
    void loginShouldReturnStatusBadCredentials() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, ERole.ROLE_USER));
        User user = new User("email@gmail.com", "username", "$2a$10$a4Gpxd0.iP1TztY9GH9bjuKRl/eLmWH/IISkeqPhbrEti5m1NkRq.", roles);

        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(1L, ERole.ROLE_USER)));

        Login login = new Login("username", "password1");

        var response1 = authController.login(login);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals("Problem with login! Bad credentials", response1.getBody());
        verify(userRepository, times(2)).findUserByUsername("username");
    }
}