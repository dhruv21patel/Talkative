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
@Table("messages")
public class Messages {

    @Id
    private UUID messageId;

    private String chatId;  // Direct reference to chatId
    private Long senderId;
    private String message;
    private Boolean seen;
    private LocalDateTime sendTime;
}
