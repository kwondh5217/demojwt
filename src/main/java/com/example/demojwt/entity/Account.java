package com.example.demojwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
@Table(name = "account")
@Entity
public class Account {

    @JsonIgnore
    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long userId;

    private String username;

    @JsonIgnore
    private String password;

    private String nickname;

    @JsonIgnore
    private boolean activated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authoritySet;
}
