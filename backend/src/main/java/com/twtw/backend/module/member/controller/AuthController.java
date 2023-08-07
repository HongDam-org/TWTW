package com.twtw.backend.module.member.controller;

import com.twtw.backend.config.security.repository.RefreshTokenRepository;
import com.twtw.backend.module.member.dto.request.OAuthRequest;
import com.twtw.backend.module.member.dto.response.TokenDto;
import com.twtw.backend.module.member.dto.request.MemberSaveRequest;
import com.twtw.backend.module.member.dto.request.TokenRequest;
import com.twtw.backend.module.member.entity.AuthType;
import com.twtw.backend.module.member.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> authorize(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(tokenRequest.getAccessToken(),tokenRequest.getRefreshToken()));
    }

    @PostMapping("/save")
    public ResponseEntity saveMember(@RequestBody MemberSaveRequest memberSaveRequest) {
        authService.saveMember(memberSaveRequest);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> afterSocialLogin(@RequestBody OAuthRequest request){
        TokenDto tokenDto = authService.getTokenByOAuth(request);

        if(tokenDto == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokenDto);
        }

        else{
            return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
        }
    }
}
