package org.example.GroupMessageService.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDTO {

    @JsonProperty("GroupName")
    public String GroupName;

    @JsonProperty("CreatorId")
    public Long CreatorId;
}
