package org.example.MessagingService.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @Column(name = "user_id", nullable = false)
    private Long userId; // User who owns the contact list

    @Column(name = "contact_user_id", nullable = false)
    private Long contactUserId; // User ID of the contact

    @Column(name = "last_message", nullable = true)
    private String lastMessage; // Last message exchanged with this contact

    @Column(name = "last_interaction")
    private Timestamp lastInteraction; // Timestamp of the last interaction

    @Column(name = "is_blocked")
    private Boolean isBlocked; // To block/unblock a contact

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt; // When the contact was added
}