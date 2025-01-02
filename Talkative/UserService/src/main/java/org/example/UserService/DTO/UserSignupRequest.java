package org.example.UserService.DTO;

import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {

    private String username; // Optional username for display

    private String passwordHash; // Encrypted password

}
