package com.example.JWT.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.JWT.Model.JwtTokens;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface JwtRepo extends MongoRepository<JwtTokens, String> {
    Optional<JwtTokens> findByToken(String token);
    Optional<JwtTokens> findByEmail(String email);
}