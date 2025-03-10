package org.example.GroupMessageService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMembersDTO {
    @JsonProperty("chatid")
    private String chatid;

    @JsonProperty("Members")
    private List<Long> membersList;
}
