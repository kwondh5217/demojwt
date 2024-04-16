package com.example.demojwt.service;

import com.example.demojwt.entity.Account;
import com.example.demojwt.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public Account saveAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return this.accountRepository.save(account);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account byUsername = accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("invalid usernmae"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(byUsername.getUsername())
                .password(byUsername.getPassword())
                .authorities(String.valueOf(byUsername.getAuthoritySet().stream()
                        .map(authority -> authority.getAuthorityName())
                        .collect(Collectors.toSet())))
                .build();
    }
}
