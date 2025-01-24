package org.example.GroupMessageService.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("chats")
public class Chats {

    @Id
    @Column("chatid")
    private String chatId;

    @Column("created_at")
    private LocalDateTime createdAt;
}
