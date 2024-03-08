package com.twtw.backend.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.twtw.backend.global.properties.FirebaseProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FcmConfig {

    private final FirebaseProperties firebaseProperties;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        log.info("FirebaseApp initializing...11111");

        final FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(
                        new ClassPathResource(firebaseProperties.getLocation()).getInputStream()))
                .build();
        log.info("FirebaseApp initializing...22222");

        if (FirebaseApp.getApps().isEmpty()) {
            log.info("FirebaseApp initializing...33333");
            return FirebaseApp.initializeApp(options);
        }
        log.info("FirebaseApp initializing...44444");
        return FirebaseApp.getInstance();
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance(firebaseApp());
    }
}
