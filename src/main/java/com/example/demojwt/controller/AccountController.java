package com.example.demojwt.controller;

import com.example.demojwt.dto.AccountDto;
import com.example.demojwt.entity.Account;
import com.example.demojwt.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<Account> signup(@Valid @RequestBody AccountDto accountDto){
        return ResponseEntity.ok(this.accountService.signup(accountDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Account> getMyUserInfo(){
        return ResponseEntity.ok(this.accountService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Account> getUserInfo(@PathVariable("username") String username){
        return ResponseEntity.ok(this.accountService.getUserWithAuthorities(username).get());
    }

}
