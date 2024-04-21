package com.example.demojwt.service;

import com.example.demojwt.dto.AccountDto;
import com.example.demojwt.entity.Account;
import com.example.demojwt.entity.Authority;
import com.example.demojwt.repository.AccountRepository;
import com.example.demojwt.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account signup(AccountDto accountDto){
        if(this.accountRepository.findOneWithAuthoritiesByUsername(accountDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Account buildAccount = Account.builder()
                .username(accountDto.getUsername())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .nickname(accountDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return this.accountRepository.save(buildAccount);
    }

    @Transactional(readOnly = true)
    public Optional<Account> getUserWithAuthorities(String username){
        return this.accountRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional
    public Optional<Account> getMyUserWithAuthorities(){
        return SecurityUtil.getCurrentUsername().flatMap(this.accountRepository::findOneWithAuthoritiesByUsername);
    }


}
