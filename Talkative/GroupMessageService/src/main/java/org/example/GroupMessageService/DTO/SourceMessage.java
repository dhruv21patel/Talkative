package org.example.InidividualMessageService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceMessage {

    @JsonProperty("Userid1")
    private Long sender_id;

    @JsonProperty("Userid2")
    private Long receiver_id;

    @JsonProperty("Is_Group")
    private Boolean IsGroup;

    @JsonProperty("Group_name")
    private String Groupname;

    @JsonProperty("Message")
    private String Message;

    private String Chatid;
}
