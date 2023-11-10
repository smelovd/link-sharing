package com.shimada.linksv4.web.controllers;

import com.shimada.linksv4.web.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jdk.jfr.Unsigned;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Unsigned
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    @Operation(summary = "Get user by username from path")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/{username}/qrcode")
    @Operation(summary = "Get qrcode (path)")
    public ResponseEntity<?> getQR(@PathVariable String username) {
        return userService.getQR(username);
    }
}
