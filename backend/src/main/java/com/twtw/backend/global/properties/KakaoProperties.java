package com.twtw.backend.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "kakao-map")
public class KakaoProperties {
    private final String url;
    private final String key;
    private final String headerPrefix;
}
