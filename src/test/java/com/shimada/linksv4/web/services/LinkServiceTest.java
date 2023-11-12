package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.Link;
import com.shimada.linksv4.models.User;
import com.shimada.linksv4.requests.link.LinkRequest;
import com.shimada.linksv4.web.repository.LinkRepository;
import com.shimada.linksv4.web.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class LinkServiceTest {

    @Autowired
    private LinkService linkService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LinkRepository linkRepository;

    @Test
    void createLink() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        LinkRequest linkRequest = new LinkRequest("youtube.com", "youtube");
        var response = linkService.createLink(linkRequest, 1L);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userRepository).findById(1L);
    }

    @Test
    void removeLink() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setId(1L);

        Link link = new Link("youtube.com", "youtube");
        user.getLinks().add(link);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(linkRepository.findById(1L)).thenReturn(Optional.of(link));


        var response = linkService.removeLink(1L, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userRepository).findById(1L);
        verify(linkRepository).findById(1L);
    }
}