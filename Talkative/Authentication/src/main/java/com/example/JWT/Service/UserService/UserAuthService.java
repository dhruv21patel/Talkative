package com.example.JWT.Service.UserService;
import com.example.JWT.DTOs.SignUpRequest;
import com.example.JWT.DTOs.SigninRequest;
import com.example.JWT.Model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthService {

    public UserDetails Signin(SigninRequest signinRequest);
    public Users Signup(SignUpRequest signUpRequest);

}
