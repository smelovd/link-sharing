package com.shimada.linksv4.web.services;

import com.shimada.linksv4.models.Link;
import com.shimada.linksv4.models.User;
import com.shimada.linksv4.requests.link.LinkRequest;
import com.shimada.linksv4.web.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final AuthService authService;

    public ResponseEntity<?> createLink(LinkRequest createLink, Long userId) {
        User user = authService.findById(userId);
        Link link = new Link(createLink.getUrl(), createLink.getName());
        user.getLinks().add(link);
        return new ResponseEntity<>("Link successfully created!", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> removeLink(Long linkId, Long userId) {
        Link link = findById(linkId);
        User user = authService.findById(userId);
        user.getLinks().remove(link);
        linkRepository.delete(link);
        return new ResponseEntity<>("Link successfully deleted!", HttpStatus.OK);
    }

    public Link findById(Long id) {
        var link = linkRepository.findById(id);
        if (link.isEmpty()) {
            throw new UsernameNotFoundException("Link not found!");
        }
        return link.get();
    }
}
