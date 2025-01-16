package org.example.InidividualMessageService.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Messages")
public class Messages {

    @Id
    private String MessageID;
    private String ChatID;
    private Long SenderID;
    private String Message;
    private Boolean Seen;
    private LocalDateTime Send_Time;

}

