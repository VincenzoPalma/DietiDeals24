package it.uninastudents.dietidealsservice.config.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FirebaseConfig {

    private static final String PATH_FIREBASE_CONFIG = "/firebase_config.json";

    @PostConstruct
    public void onStart() {
        try {
            log.info("Initializing Firebase App...");
            initializeFirebaseApp();
        } catch (IOException e) {
            log.error("Something went wrong while initializing Firebase...", e);
        }
    }

    private void initializeFirebaseApp() throws IOException {
        if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
            try (var serviceAccount = FirebaseConfig.class.getResourceAsStream(PATH_FIREBASE_CONFIG)) {
                if (serviceAccount != null) {
                    GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(credentials)
                            .build();
                    FirebaseApp.initializeApp(options);
                    log.info("Firebase has been initialized!");
                }
            }
        }

    }

}