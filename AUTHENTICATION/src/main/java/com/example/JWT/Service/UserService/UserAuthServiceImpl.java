package com.example.JWT.Service.UserService;

import com.example.JWT.DTOs.SignUpRequest;
import com.example.JWT.DTOs.SigninRequest;
import com.example.JWT.Model.Users;
import com.example.JWT.Repository.UsersRepo;
import com.example.JWT.Service.RedisService.RedisService;
import com.example.JWT.exception.UserAlreadyExistsException;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserAuthServiceImpl implements UserAuthService{

    @Autowired
    private  UsersRepo usersRepo;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private  PasswordEncoder encoder;

    @Autowired
    private  RedisService redisService;

//    @Autowired
//    public UserAuthServiceImpl(UsersRepo usersRepo, AuthenticationManager authenticationManager, PasswordEncoder encoder, RedisService redisService) {
//        this.usersRepo = usersRepo;
//        this.authenticationManager = authenticationManager;
//        this.encoder = encoder;
//        this.redisService = redisService;
//    }

    @Override
    public UserDetails Signin(SigninRequest signinRequest) throws UsernameNotFoundException {
        Users user ;
        user = redisService.get(signinRequest.getEmail(),Users.class);
        if(user == null)
        {
            user = usersRepo.findByEmail(signinRequest.getEmail());
            redisService.set(signinRequest.getEmail(), user);
            if(user == null)
            {
                System.out.println("user not found");
                throw new UsernameNotFoundException("user not Found");
            }
        }

        System.out.println("REQUESTED User -> "+user.getUsername());


            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (UserDetails) user;

    }

    @Override
    public Users Signup(SignUpRequest signUpRequest) throws UsernameNotFoundException {

        System.out.println("REQUEST SignUp -> " + signUpRequest);
        Optional<Users> user = Optional.ofNullable( usersRepo.findByEmail( signUpRequest.getEmail() ) );

        if(user.isPresent())
        {
            throw new UserAlreadyExistsException("USER ALREADY EXIST");
        }else{

            Users newuser = new Users();
            newuser.setFirstname(signUpRequest.getFirstname());
            newuser.setLastName(signUpRequest.getLastname());
            newuser.setEmail(signUpRequest.getEmail());
            newuser.setPassword(encoder.encode(signUpRequest.getPassword()));
            usersRepo.save(newuser);


            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(),signUpRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return newuser;
        }

    }


}
