package com.twtw.backend.domain.member.controller;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.request.TokenRequest;
import com.twtw.backend.domain.member.dto.response.AfterLoginDto;
import com.twtw.backend.domain.member.dto.response.TokenDto;
import com.twtw.backend.domain.member.service.AuthService;

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
    public ResponseEntity<TokenDto> authorize(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(
                authService.refreshToken(
                        tokenRequest.getAccessToken(), tokenRequest.getRefreshToken()));
    }

    @PostMapping("/save")
    public ResponseEntity<TokenDto> saveMember(@RequestBody MemberSaveRequest memberSaveRequest) {
        TokenDto tokenDto = authService.saveMember(memberSaveRequest);

        return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AfterLoginDto> afterSocialLogin(@RequestBody OAuthRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.getTokenByOAuth(request));
    }
}
