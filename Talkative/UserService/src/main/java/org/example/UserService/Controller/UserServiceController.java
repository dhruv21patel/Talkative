package org.example.UserService.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.UserService.DTO.SearchRequest;
import org.example.UserService.DTO.UserResponse;
import org.example.UserService.DTO.UserSignupRequest;
import org.example.UserService.Models.Users;
import org.example.UserService.Repository.UserServiceRepo;
import org.example.UserService.Service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/User")
public class UserServiceController {

    @Autowired
    private  UserServiceRepo userServiceRepo;

    @Autowired
    private  MappingService mappingService;

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> GetUser()
    {
        List<Users> users = userServiceRepo.findAll();
        List<UserResponse> response = users.stream()
            .map(user -> mappingService.MapResponse(user))
            .toList();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/SaveUser")
    public ResponseEntity<UserResponse> SaveUser(@RequestBody UserSignupRequest userSignupRequest)
    {
        Users mappeduser = mappingService.mapUserRequest(userSignupRequest);
        Users user = userServiceRepo.save(mappeduser);
        return ResponseEntity.ok().body(mappingService.MapResponse(user));
    }


    @GetMapping("/Search/{username}")
    public ResponseEntity<List<UserResponse>> SearchUser(@PathVariable String username)
    {
        List<Users> users = userServiceRepo.findByUsernameIgnoreCase(username);
        List<UserResponse> response = users.stream()
            .map(user -> mappingService.MapResponse(user))
            .toList();
        return ResponseEntity.ok().body(response);
    }
}
