package org.example.ConnectionService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RequestConnection implements Serializable {

    @JsonProperty("Userid1")
    private Long sender_id;

    @JsonProperty("Userid2")
    private Long receiver_id;

    @JsonProperty("Is_Group")
    private Boolean IsGroup;

    @JsonProperty("Group_name")
    private String Groupname;


}
