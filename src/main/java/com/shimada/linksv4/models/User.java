package com.shimada.linksv4.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shimada.linksv4.requests.auth.Register;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String username;

    @JsonIgnore
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Link> links = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Role> roles;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String email, String username, String password, Set<Role> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(Register register, String encode) {
        this.email = register.getEmail();
        this.username = register.getUsername();
        this.password = encode;
        this.roles = new HashSet<>();
    }
}
