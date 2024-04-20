package com.example.demojwt.repository;

import com.example.demojwt.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<Account> findOneWithAuthoritiesByUsername(String username);
}
