package com.example.JWT.Service.JwtService;

import com.example.JWT.Repository.UsersRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import java.util.Date;

@Service
public class JwtTokenImpl implements JwtGenerationService{

    @Autowired
    UsersRepo usersRepo;

    @Value("${SECRET_KEY}")
    String SECRET_KEY;

    @Override
    public String generatetoken(String username) {
//        return "324243y5782839157";

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public Boolean verifyToken(String token , String username) {
        return (username.equals(Extractusername(token)) && !isTokenExpired(token));
    }

    @Override
    public String Extractusername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()// Set the secret key for signature verification
                .parseClaimsJws(token) // Parse the JWT and get claims
                .getBody();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare token expiration date with current date
    }

    // Extract expiration date from JWT
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration(); // Getting expiration date from the claims
    }

    // Extract roles (or any other claim) from JWT
    public Claims extractClaims(String token) {
        return extractAllClaims(token); // Return all claims from the token
    }

    // Check if the token has expired
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}


