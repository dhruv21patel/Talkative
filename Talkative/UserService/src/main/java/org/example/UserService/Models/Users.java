package org.example.UserService.Models;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long userId;

    @Column(name = "username")
    private String username; // Optional username for display

    @Column(name = "profile_picture_url")
    private String profilePictureUrl; // URL for profile picture storage

    @Column(name = "status")
    private String status; // User status like "Hey there! I am using WhatsApp"

    @Column(name = "last_seen")
    private Timestamp lastSeen; // Last active timestamp

    @Column(name = "password_hash", nullable = false)
    private String passwordHash; // Encrypted password

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt; // Timestamp when the user was created

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}