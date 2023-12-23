package com.twtw.backend.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FcmConfig {
    private final ClassPathResource firebaseResource = new ClassPathResource(
            "backend/src/main/resources/firebase/twtw_firebase_key.json"
    );

    @Bean
    FirebaseApp firebaseApp() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(firebaseResource.getInputStream()))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
