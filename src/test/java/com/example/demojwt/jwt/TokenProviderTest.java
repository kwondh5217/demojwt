package com.example.demojwt.jwt;

import com.example.demojwt.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    private Authentication authentication;



    @Test
    void createToken() {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        this.authentication = new UsernamePasswordAuthenticationToken("user", "password", authorities);

        String token = this.tokenProvider.createToken(authentication);
        System.out.println(token);
        assertThat(token).isNotNull();

        boolean b = this.tokenProvider.validateToken(token);
        assertThat(b).isTrue();
    }



}