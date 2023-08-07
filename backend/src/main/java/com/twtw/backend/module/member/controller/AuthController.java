package com.twtw.backend.module.member.controller;

import com.twtw.backend.module.member.dto.TokenDto;
import com.twtw.backend.module.member.dto.request.MemberRequest;
import com.twtw.backend.module.member.dto.request.TokenRequest;
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
    public ResponseEntity saveMember(@RequestBody MemberRequest memberRequest) {
        authService.saveMember(memberRequest);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/check/{id}")
    public ResponseEntity<TokenDto> checkMember(@PathVariable(name = "id") String uuid){
        try{
            TokenDto tokenDto = authService.checkMember(UUID.fromString(uuid));
            return ResponseEntity.ok(tokenDto);
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // TODO("OAuth2Info 를 이용하여 토큰 발급 메소드");
    // TODO("SOFT DELETE 구현");

}
