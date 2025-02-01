package org.example.GroupMessageService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.GroupMessageService.Models.Members;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMembersDTO {
    @JsonProperty("chatid")
    private String chatid;

    @JsonProperty("Members")
    private List<Long> membersList;
}
