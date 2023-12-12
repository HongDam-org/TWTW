package com.twtw.backend.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.cloud.gcp.storage")
public class StorageProperties {
    private final String bucket;
    private final String credentialsFile;
    private final String storageUrl;
}
