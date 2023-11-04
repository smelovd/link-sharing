package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.User;
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
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        var response = userService.getUser("username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getQR() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findUserByUsername("username")).thenReturn(Optional.of(user));

        var response = userService.getQR("username");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userRepository).findUserByUsername("username");
    }
}