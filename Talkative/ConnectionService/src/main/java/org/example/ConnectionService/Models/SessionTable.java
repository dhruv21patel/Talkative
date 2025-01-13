package org.example.ConnectionService.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "SessionTable")
public class SessionTable {

    @Id
    private Long userID;
    private String roomID;
    private LocalTime lastSeen;
    private Boolean is_Active;
}
