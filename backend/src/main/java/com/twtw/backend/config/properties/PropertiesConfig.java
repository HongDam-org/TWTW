package com.twtw.backend.config.properties;

import com.twtw.backend.global.properties.KakaoProperties;
import com.twtw.backend.global.properties.NaverProperties;
import com.twtw.backend.global.properties.TmapProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({NaverProperties.class, KakaoProperties.class, TmapProperties.class})
public class PropertiesConfig {}
