package com.shimada.linksv4.web.controllers;

import com.shimada.linksv4.requests.auth.Login;
import com.shimada.linksv4.requests.auth.Register;
import com.shimada.linksv4.web.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<?> register(@RequestBody Register register) {
        return authService.register(register);
    }

    @PostMapping("/login")
    @Operation(summary = "Login by username and password")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return authService.login(login);
    }
}
