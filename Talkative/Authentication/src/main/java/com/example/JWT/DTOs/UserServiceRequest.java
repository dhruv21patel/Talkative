package com.example.JWT.DTOs;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserServiceRequest {

    private String username; // Optional username for display

    private String passwordHash; // Encrypted password

}
