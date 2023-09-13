package com.twtw.backend.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix ="naver-map")
public class NaverProperties {
    private final String id;
    private final String secret;
    private final String url;
    private final String headerClientId;
    private final String headerClientSecret;
}
