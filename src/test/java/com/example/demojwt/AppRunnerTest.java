package com.example.demojwt;

import com.example.demojwt.entity.Account;
import com.example.demojwt.repository.AuthorityRepository;
import com.example.demojwt.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppRunnerTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AuthorityRepository authorityRepository;

    @DisplayName("application 실행 시 초기 데이터가 저장된다")
    @Test
    void init() {
        Optional<Account> byId = this.accountRepository.findById(1L);
        assertThat(byId).isNotEmpty();
        Account account = byId.get();
        assertThat(account.getAuthorities().size()).isEqualTo(2);
    }

}