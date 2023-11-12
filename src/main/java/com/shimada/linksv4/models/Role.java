package com.shimada.linksv4.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private ERole role;


    public Role(ERole role) {
        this.role = role;
    }

    public Role(Long id, ERole role) {
        this.id = id;
        this.role = role;
    }
}
