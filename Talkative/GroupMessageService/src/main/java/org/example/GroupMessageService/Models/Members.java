package org.example.GroupMessageService.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "group_members", uniqueConstraints = @UniqueConstraint(columnNames = {"chatid", "userid"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Members {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chatid", nullable = false)
    private Chats chat;

    @Column(name = "userid", nullable = false)
    private Long userId;

    @Column(name = "role", nullable = false)
    private String role = "member";

    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();
}