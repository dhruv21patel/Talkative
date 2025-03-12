package org.example.ConnectionService.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String sender;
    private String content;
    private String roomId;  // Ensure messages are tied to a room
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}