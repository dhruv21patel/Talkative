package com.example.JWT.Service.JwtService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtGenerationService {

    public String generatetoken(String username);
    public Boolean verifyToken(String token , String Username);
    public String Extractusername(String token);
}
