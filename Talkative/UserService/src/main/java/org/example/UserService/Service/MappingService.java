package org.example.UserService.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.RequiredArgsConstructor;
import org.example.UserService.DTO.UserResponse;
import org.example.UserService.DTO.UserSignupRequest;
import org.example.UserService.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MappingService {

//    @Autowired
//    private final BCrypt.Verifyer passwordverifier;

    @Autowired
    private final BCrypt.Hasher passwordhasher;

    public Users mapUserRequest(UserSignupRequest userSignupRequest)
    {
        Users newuser =  new Users();
        newuser.setUsername(userSignupRequest.getUsername());
        newuser.setPasswordHash(passwordhasher.hashToString(12,userSignupRequest.getPasswordHash().toCharArray() ));
        return newuser;
    }

    public UserResponse MapResponse(Users user)
    {
        return UserResponse.builder()
                    .username(user.getUsername())
                    .passwordHash(user.getPasswordHash())
                    .profilePictureUrl(user.getProfilePictureUrl())
                    .updatedAt(user.getUpdatedAt())
                    .createdAt(user.getCreatedAt())
                    .lastSeen(user.getLastSeen())
                    .status(user.getStatus())
                    .build();
    }
}
