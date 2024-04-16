package com.example.demojwt.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
@Table(name = "authority")
@Entity
public class Authority {

    @Id
    @Column(name = "authority_name")
    private String authorityName;
}
