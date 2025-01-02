package org.example.MessagingService.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId; // References Users from User Service

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId; // References Users from User Service

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "is_read")
    private Boolean isRead;

    @CreationTimestamp
    @Column(name = "sent_at")
    private Timestamp sentAt;

    // Enum for Content Types
    public enum ContentType {
        TEXT, IMAGE, VIDEO, DOCUMENT
    }
}

