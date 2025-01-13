package com.example.JWT.Service.UserService;

import com.example.JWT.Config.RestTemplateConfig;
import com.example.JWT.DTOs.SignUpRequest;
import com.example.JWT.DTOs.SigninRequest;
import com.example.JWT.DTOs.UserServiceRequest;
import com.example.JWT.Model.Users;
import com.example.JWT.Repository.UsersRepo;
import com.example.JWT.Service.RedisService.RedisService;
import com.example.JWT.exception.UserAlreadyExistsException;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService{

    @Autowired
    private final UsersRepo usersRepo;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private final RedisService redisService;

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${user_Service_uri}")
    private String User_service_uri;

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

            UserServiceRequest userServiceRequest = UserServiceRequest.builder().username(signUpRequest.getEmail()).passwordHash(signUpRequest.getPassword()).build();
//            Boolean status = SendRESTRequest(restTemplate,userServiceRequest);
//
//            if (!status)
//            {
//                throw new InternalError("UserServiceDown");
//            }
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

    private Boolean SendRESTRequest(RestTemplate restTemplate,UserServiceRequest userServiceRequest)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserServiceRequest> request = new HttpEntity<>(userServiceRequest, headers);
        String url = User_service_uri + "/User/SaveUser";
        ResponseEntity<String> response = null;
        try{
             response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
        }catch (Exception E)
        {
            throw new RuntimeException("Error ON URL or Request" + url);
        }
        return response.getStatusCode().is2xxSuccessful();

    }

}
