package com.twtw.backend.domain.member.controller;

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
        return ResponseEntity.ok(
                authService.refreshToken(
                        tokenRequest.getAccessToken(), tokenRequest.getRefreshToken()));
    }

    @PostMapping("/save")
    public ResponseEntity<AfterLoginResponse> saveMember(
            @RequestBody @Valid MemberSaveRequest memberSaveRequest) {
        AfterLoginResponse tokenDto = authService.saveMember(memberSaveRequest);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
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
}
