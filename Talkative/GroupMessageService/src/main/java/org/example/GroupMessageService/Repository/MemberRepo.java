
package org.example.GroupMessageService.Repository;

import org.example.GroupMessageService.Models.Members;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface MemberRepo extends ReactiveCrudRepository<Members, UUID> {
}
