package com.shimada.linksv4.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
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
}
