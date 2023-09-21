package com.twtw.backend.config.properties;

import com.twtw.backend.global.properties.*;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    NaverProperties.class,
    KakaoProperties.class,
    TmapProperties.class,
    RabbitMQProperties.class,
    RedisProperties.class
})
public class PropertiesConfig {}
