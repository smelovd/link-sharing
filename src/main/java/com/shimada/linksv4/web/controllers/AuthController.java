package com.shimada.linksv4.web.controllers;

import com.shimada.linksv4.requests.auth.Login;
import com.shimada.linksv4.requests.auth.Register;
import com.shimada.linksv4.web.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 360000)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<?> register(@RequestBody @Valid @NotNull Register register) throws CredentialException {
        return authService.register(register);
    }


    @PostMapping("/login")
    @Operation(summary = "Login by username and password")
    public ResponseEntity<?> login(@RequestBody Login login) throws AuthenticationException {
        return authService.login(login);
    }
}
