package com.shimada.linksv4.responses;

import com.shimada.linksv4.models.User;
import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String username;
    private String accessToken;

    public JwtResponse(User user, String accessToken) {
        this.username = user.getUsername();
        this.id = user.getId();
        this.accessToken = accessToken;
    }
}
