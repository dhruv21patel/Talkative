package com.example.JWT.Service.UserService;

import com.example.JWT.Model.Users;
import com.example.JWT.Repository.UsersRepo;
import com.example.JWT.Service.RedisService.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;

@Service
public class Getuser implements UserDetailsService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    RedisService redisService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user;
        user = redisService.get(username,Users.class);
        if(user == null)
        {
            user =  usersRepo.findByEmail(username);
        }

        if(user == null)
        {
            throw new UsernameNotFoundException("user not there");
        }

        return user;
    }
}
