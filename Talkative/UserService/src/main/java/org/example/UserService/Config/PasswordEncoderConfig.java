package org.example.UserService.Config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PasswordEncoderConfig {

     @Bean
    public BCrypt.Hasher passwordHasher() {
        return BCrypt.withDefaults(); // Provides the hasher
    }

    @Bean
    public BCrypt.Verifyer passwordVerifier() {
        return BCrypt.verifyer(); // Provides the verifier
    }
}
