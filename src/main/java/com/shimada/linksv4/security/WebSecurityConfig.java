package com.shimada.linksv4.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    //private final JwtTokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(s ->
                        s
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(conf ->
                        conf
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    response.getWriter().write("Unauthorized");
                                })
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.getWriter().write("Forbidden");
                                }))
                .authorizeHttpRequests(conf ->
                        conf
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/user/**").permitAll()
                                //.requestMatchers("/api/link/**").authenticated()
                                .anyRequest().permitAll()
                )
                //.addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
