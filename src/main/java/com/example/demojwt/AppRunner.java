package com.example.demojwt;

import com.example.demojwt.entity.Account;
import com.example.demojwt.entity.Authority;
import com.example.demojwt.repository.AuthorityRepository;
import com.example.demojwt.service.AccountService;
import com.example.demojwt.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final AccountService accountService;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args){
        Authority role_user = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        Authority role_admin = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();
        Authority saveUser = this.authorityRepository.save(role_user);
        Authority saveAdmin = this.authorityRepository.save(role_admin);

        Account account = Account.builder()
                .userId(1L)
                .username("daeho")
                .password("pass")
                .activated(true)
                .authorities(Set.of(saveUser, saveAdmin))
                .build();
        this.accountService.saveAccount(account);
    }
}
