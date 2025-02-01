package org.example.InidividualMessageService.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messages1")  // ✅ Ensure lowercase table name
public class Messages {

    @Id
    @Column("messageid")  // ✅ Match PostgreSQL column name
    private UUID MessageID;

    @Column("chatid")
    private String ChatID;

    @Column("senderid")
    private Long SenderID;

    @Column("message")
    private String Message;

    @Column("seen")
    private Boolean Seen;

    @Column("sendTime")
    private Timestamp Send_Time;
}

