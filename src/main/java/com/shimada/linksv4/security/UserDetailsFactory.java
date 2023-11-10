package com.shimada.linksv4.security;

import com.shimada.linksv4.models.Role;
import com.shimada.linksv4.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsFactory {
    public static UserDetailsImpl create(User user) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                list
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRole)
                .map(Objects::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
