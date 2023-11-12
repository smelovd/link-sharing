package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.User;
import com.shimada.linksv4.web.controllers.UserController;
import com.shimada.linksv4.web.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getUserShouldReturnOK() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        var response = userController.getUser("username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getUserShouldReturnNotFoundUser() {
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.empty());

        var response = userController.getUser("username");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Problem with getUser! User not found!", response.getBody());
        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getQRShouldReturnOK() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        var response = userController.getQR("username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getQRShouldReturnNotFoundUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.empty());

        var response = userController.getQR("username");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Problem with getQR! User not found!", response.getBody());
        verify(userRepository).findUserByUsername("username");
    }
}