package com.example.JWT.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "jwt_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtTokens {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id; // Unique identifier for the document

    private String email; // Email associated with the token

    private String token; // The JWT string

    private Date expiresAt; // Token expiration time

    private Date createdAt = new Date(); // Token creation time
}
