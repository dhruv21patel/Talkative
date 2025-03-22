package org.example.ConnectionService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMessageDTO {

    @JsonProperty("Userid1")
    private Long sender_id;

    @JsonProperty("Userid2")
    private Long receiver_id;

    @JsonProperty("Is_Group")
    private Boolean IsGroup;

    @JsonProperty("Group_name")
    private String Groupname;

}
