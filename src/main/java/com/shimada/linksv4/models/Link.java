package com.shimada.linksv4.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Link {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private String name;

    public Link(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
