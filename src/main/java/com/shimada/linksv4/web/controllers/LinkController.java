package com.shimada.linksv4.web.controllers;

import com.shimada.linksv4.requests.link.LinkRequest;
import com.shimada.linksv4.web.services.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/link")
@RequiredArgsConstructor
@PreAuthorize("#userId == authentication.principal.id")
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/{userId}/create")
    @Operation(summary = "Create link")
    public ResponseEntity<?> createLink(@RequestBody LinkRequest createLink, @PathVariable Long userId) {
        return linkService.createLink(createLink, userId);
    }

    @DeleteMapping("/{userId}/{linkId}")
    @Operation(summary = "Delete link by linkId from path")
    public ResponseEntity<?> removeLink(@PathVariable Long linkId, @PathVariable Long userId) {
        return linkService.removeLink(linkId, userId);
    }
}

