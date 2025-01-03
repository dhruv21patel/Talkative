package com.example.JWT.Controller;

import com.example.JWT.DTOs.SignUpRequest;
import com.example.JWT.DTOs.SigninRequest;
import com.example.JWT.Model.Users;
import com.example.JWT.Repository.JwtRepo;
import com.example.JWT.Service.JwtService.JwtDbImpl;
import com.example.JWT.Service.JwtService.JwtGenerationService;
import com.example.JWT.Service.JwtService.Jwtdatabaseinterface;
import com.example.JWT.Service.UserService.UserAuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Date;

@RestController
@RequestMapping("/Auth")
public class userlogin {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private JwtGenerationService jwt;

    @Value("${EXPIRATION_TIME}")
    private Integer EXPIRATION_TIME;

    @Autowired
    private Jwtdatabaseinterface Jwtdb;


    @PostMapping("/Login")
    public ResponseEntity<String> LoginRequest(@RequestBody SigninRequest signinRequest, HttpServletResponse response)
    {
        UserDetails signin = userAuthService.Signin(signinRequest);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            //jwt expiration crosscheck
            String JWTTOKEN =  jwt.generatetoken(signin.getUsername());
            Jwtdb.saveToken(signin.getUsername(),JWTTOKEN,EXPIRATION_TIME);

            //setting a cookie
            setcookie(JWTTOKEN, response);

            return ResponseEntity.ok()
                .body("Authentication successful");
            }

        return ResponseEntity.status(500).body("No authenticated user");
    }


    @PostMapping("/Signup")
    public ResponseEntity<String> SignupRequest(@RequestBody SignUpRequest signUpRequest,HttpServletResponse response)
    {
        Users Signup = userAuthService.Signup(signUpRequest);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            response.setStatus(200);
            return ResponseEntity.ok()
                    .body("new USER CREATED AND AUTHENTICATED");
        }
        return (ResponseEntity<String>) ResponseEntity.status(500).body("USER ALREADY EXIST");
    }



      private void setcookie (String JWTTOKEN,HttpServletResponse response)
    {
        Cookie jwtCookie = new Cookie("authToken", JWTTOKEN);
            jwtCookie.setHttpOnly(true); // Prevent access via JavaScript
            jwtCookie.setSecure(true); // Transmit only over HTTPS
            jwtCookie.setDomain("example.com"); // Set your domain here
            jwtCookie.setPath("/"); // Available to all paths
            jwtCookie.setMaxAge((int) (EXPIRATION_TIME / 1000)); // Expiry in seconds


        //response set
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + JWTTOKEN);
            response.addCookie(jwtCookie);
            response.setStatus(200);

    }
}
