package org.example.GroupMessageService.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("group_members")
public class Members {

    @Id
    private UUID id;

    @Column("chatid")
    private String chatId;  // Direct reference to chatId

    @Column("userid")
    private Long userId;

    private String role;

    @Column("joined_at")
    private LocalDateTime joinedAt;
}
