package com.shimada.linksv4.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private Long access;
    private Long refresh;
}
