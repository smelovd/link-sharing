package com.shimada.linksv4.web.services;

import com.shimada.linksv4.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> getUser(String username) {
        var user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> getQR(String username) {
        var user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(
                "https://api.qrserver.com/v1/create-qr-code/?data=http://localhost:8080/api/user/" + username + "&size=150x150",
                byte[].class
        );
    }
}
