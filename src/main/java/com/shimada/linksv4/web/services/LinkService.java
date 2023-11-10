package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.Link;
import com.shimada.linksv4.web.repository.LinkRepository;
import com.shimada.linksv4.web.repository.UserRepository;
import com.shimada.linksv4.requests.link.LinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;

    @Transactional
    public ResponseEntity<?> createLink(LinkRequest createLink, Long userId) {
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Link link = new Link(createLink.getUrl(), createLink.getName());
        user.get().getLinks().add(link);
        return new ResponseEntity<>("Link successfully created!", HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity<?> patchLink(Long linkId, LinkRequest patchLink) {
        var link = linkRepository.findById(linkId).orElse(null);
        if (link == null) {
            return new ResponseEntity<>("Link not found!", HttpStatus.NOT_FOUND);
        }
        link.setName(patchLink.getName());
        link.setUrl(patchLink.getUrl());
        linkRepository.save(link);
        return new ResponseEntity<>("Link successfully patched!", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> removeLink(Long linkId, Long userId) {
        var link = linkRepository.findById(linkId).orElse(null);
        if (link == null) {
            return new ResponseEntity<>("Link not found!", HttpStatus.NOT_FOUND);
        }
        var user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        user.getLinks().remove(link);
        linkRepository.delete(link);
        return new ResponseEntity<>("Link successfully deleted!", HttpStatus.OK);
    }

}
