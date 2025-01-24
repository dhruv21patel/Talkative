package org.example.GroupMessageService.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
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

    private String chatId;  // Direct reference to chatId
    private Long userId;
    private String role;
    private LocalDateTime joinedAt;
}
