package com.twtw.backend.config.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.twtw.backend.global.properties.StorageProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    public Storage storage() throws IOException {
        final GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(
                        new ClassPathResource(storageProperties.getCredentialsFile())
                                .getInputStream());

        return StorageOptions.newBuilder().setCredentials(googleCredentials).build().getService();
    }
}
