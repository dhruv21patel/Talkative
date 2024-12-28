package com.example.JWT.Model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "jwt_tokens")
public class JwtTokens {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id; // Unique identifier for the document

    private String email; // Email associated with the token

    private String token; // The JWT string

    private Date expiresAt; // Token expiration time

    private Date createdAt = new Date(); // Token creation time
}
