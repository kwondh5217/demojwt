package com.example.demojwt.service;

import com.example.demojwt.entity.Account;
import com.example.demojwt.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public Account saveAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return this.accountRepository.save(account);
    }
}
