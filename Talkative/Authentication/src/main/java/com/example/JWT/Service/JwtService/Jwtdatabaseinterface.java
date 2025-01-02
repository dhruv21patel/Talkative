package com.example.JWT.Service.JwtService;

import com.example.JWT.Model.JwtTokens;

public interface Jwtdatabaseinterface {

     public JwtTokens saveToken(String email, String token, Integer expiresAt);
     public boolean validateToken(String token);
     public void deleteToken(String token);
}
