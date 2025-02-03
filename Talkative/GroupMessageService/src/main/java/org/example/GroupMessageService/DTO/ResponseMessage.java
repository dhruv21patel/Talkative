package org.example.GroupMessageService.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {

    private Long sender_id;

    private String Groupname;

    private String Message;

    private Boolean seen;

    private LocalDateTime sendtime;
}
