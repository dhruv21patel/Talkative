package org.example.UserService.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String username; // Optional username for display

    private String profilePictureUrl; // URL for profile picture storage

    private String status; // User status like "Hey there! I am using WhatsApp"

    private Timestamp lastSeen; // Last active timestamp

    private String passwordHash; // Encrypted password

    private Timestamp createdAt; // Timestamp when the user was created

    private Timestamp updatedAt;
}
