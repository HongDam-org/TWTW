package com.twtw.backend.domain.member.controller;

import com.twtw.backend.domain.member.dto.request.DeviceTokenRequest;
import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.request.TokenRequest;
import com.twtw.backend.domain.member.dto.response.AfterLoginResponse;
import com.twtw.backend.domain.member.dto.response.TokenDto;
import com.twtw.backend.domain.member.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> authorize(@RequestBody @Valid TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRequest));
    }

    @PostMapping("/save")
    public ResponseEntity<AfterLoginResponse> saveMember(
            @RequestBody @Valid MemberSaveRequest memberSaveRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.saveMember(memberSaveRequest));
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validate() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AfterLoginResponse> afterSocialLogin(
            @RequestBody @Valid OAuthRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getTokenByOAuth(request));
    }

    @PostMapping("/device")
    public ResponseEntity<Void> updateDeviceToken(@RequestBody DeviceTokenRequest request) {
        authService.updateDeviceToken(request);
        return ResponseEntity.noContent().build();
    }
}
