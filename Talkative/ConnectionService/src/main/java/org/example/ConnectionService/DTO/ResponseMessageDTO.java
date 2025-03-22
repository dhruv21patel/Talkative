package org.example.ConnectionService.DTO;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class ResponseMessageDTO {

    private Long messageId;
    private String chatId;
    private Long senderId;
    private String message;
    private boolean seen;
    private LocalDateTime sendTime;  // Use String or LocalDateTime

    // Getters and Setters
}

