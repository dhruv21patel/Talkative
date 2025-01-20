package org.example.InidividualMessageService.Repository;

import org.example.InidividualMessageService.Models.Messages;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import java.util.UUID;

public interface MessageRepo extends ReactiveCrudRepository<Messages, UUID> {
}
