package com.example.demojwt.service;

import com.example.demojwt.entity.Account;
import com.example.demojwt.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findOneWithAuthoritiesByUsername(username)
                .map(account -> createUser(username, account))
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    private UserDetails createUser(String username, Account account){
        if(!account.isActivated()){
            throw new RuntimeException("it is not activate");
        }

        List<GrantedAuthority> collect = account.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities(collect)
                .build();
    }
}
