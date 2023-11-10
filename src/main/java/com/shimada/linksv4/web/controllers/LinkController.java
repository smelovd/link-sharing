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
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/{userId}/create")
    @Operation(summary = "Create link")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> createLink(@RequestBody LinkRequest createLink, @PathVariable Long userId) {
        return linkService.createLink(createLink, userId);
    }

    @DeleteMapping("/{userId}/{linkId}")
    @Operation(summary = "Delete link by linkId from path")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> removeLink(@PathVariable Long linkId, @PathVariable Long userId) {
        return linkService.removeLink(linkId, userId);
    }

    @PatchMapping("/{ignoredUserId}/{linkId}")
    @Operation(summary = "Updating(patching) link by linkId from path")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> patchLink(@PathVariable Long linkId, @RequestBody LinkRequest patchLink) {
        return linkService.patchLink(linkId, patchLink);
    }
}

