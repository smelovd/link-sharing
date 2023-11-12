package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;

    @Transactional
    public ResponseEntity<?> getUser(String username) {
        User user = authService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> getQR(String username) {
        User user = authService.findUserByUsername(username);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity("https://api.qrserver.com/v1/create-qr-code/?data=http://localhost:8080/api/user/" + username + "&size=150x150", byte[].class);
    }
}
