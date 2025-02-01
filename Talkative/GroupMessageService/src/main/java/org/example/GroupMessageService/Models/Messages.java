package org.example.GroupMessageService.Models;

import jakarta.persistence.GeneratedValue;
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
@Table("messages")
public class Messages {

    @Id
    @Column("messageid")
    private UUID messageId;

    @Column("chatid")
    private String chatId;  // Direct reference to chatId

    @Column("senderid")
    private Long senderId;

    private String message;
    private Boolean seen;

    @Column("send_time")
    private LocalDateTime sendTime;
}
