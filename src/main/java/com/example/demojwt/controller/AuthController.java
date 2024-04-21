package com.example.demojwt.controller;


import com.example.demojwt.dto.LoginDto;
import com.example.demojwt.dto.TokenDto;
import com.example.demojwt.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /* 요청 흐름
    authenticationManagerBuilder로 authenticate를 만드는 과정에서 요청으로 들어온 loginDto의 username, password를 가지고
    providerManager를 호출함.
    providerManager는 provider를 컬렉션을 가지고 있음. 컬렉션을 돌면서 userDetailsService의 loadUserByUsername 메서드를 호출하여 인증절차를 거침
    인증이 완료되면 authenticate 를 SecurityContext에 저장하고 토큰을 만들어서 사용자에게 반환
     */
    @PostMapping("/api/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = tokenProvider.createToken(authenticate);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(new TokenDto(token), httpHeaders, HttpStatus.OK);
    }


}
