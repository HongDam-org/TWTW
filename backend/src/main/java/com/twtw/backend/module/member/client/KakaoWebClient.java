package com.twtw.backend.module.member.client;

import com.twtw.backend.module.member.dto.client.KakaoResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


import java.nio.charset.StandardCharsets;

@Component
public class KakaoWebClient {
    private final WebClient webclient;

    public KakaoWebClient() {
        this.webclient = generateWebClient();
    }

    public KakaoResponse requestKakao(final String token){
        return webclient
                .get()
                .headers(headers -> headers.setBearerAuth(token))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(KakaoResponse.class)
                .blockOptional()
                .orElseThrow(RuntimeException::new);
    }

    private WebClient generateWebClient(){
        return WebClient.builder()
                .baseUrl("https://kapi.kakao.com/v2/user/me")
                .build();
    }
}
