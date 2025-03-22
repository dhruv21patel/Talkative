package org.example.IndividualService.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {

    private Long sender_id;

    private String Chatname;

    private String Message;

    private Boolean seen;

    private Timestamp sendtime;
}
