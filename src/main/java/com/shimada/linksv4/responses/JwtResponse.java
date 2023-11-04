package com.shimada.linksv4.responses;

import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String username;
    private String accessToken;
}
