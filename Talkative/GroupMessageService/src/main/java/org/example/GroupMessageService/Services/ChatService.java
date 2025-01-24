package org.example.GroupMessageService.Services;

import org.example.GroupMessageService.Models.Chats;
import org.example.GroupMessageService.Models.Members;
import org.example.GroupMessageService.Models.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private org.example.GroupMessageService.Repository.ChatRepo chatRepo;

    @Autowired
    private org.example.GroupMessageService.Repository.MessageRepo messagesRepository;

    @Autowired
    private org.example.GroupMessageService.Repository.MemberRepo membersRepository;

    /**
     * Create a new chat
     */
    public Mono<Chats> createChat(String chatId) {

        if (chatId == null || chatId.isBlank()) {
        return Mono.error(new IllegalArgumentException("Chat ID cannot be null or empty"));
        }

//        Chats chat = Chats.builder()
//                .chatId(chatId)
//                .createdAt(LocalDateTime.now())
//                .build();
        return chatRepo.existsById(chatId) // ✅ Check if chatId exists first
                        .flatMap(exists -> {
                            if (exists) {
                                return chatRepo.findById(chatId);
                            }
                            return chatRepo.insertChat(chatId,LocalDateTime.now());
            })
            .doOnSuccess(c -> System.out.println("Chat created with chatId: " + chatId))
            .doOnError(Throwable::printStackTrace);
    }

    /**
     * Retrieve a chat by chatId
     */
    public Mono<Chats> getChatById(String chatId) {
        return chatRepo.findByChatId(chatId);
    }

    /**
     * Save a message in a chat
     */
    public Mono<Messages> saveMessage(String chatId, Long senderId, String messageText) {
        Messages message = Messages.builder()
                .messageId(UUID.randomUUID())
                .chatId(chatId)
                .senderId(senderId)
                .message(messageText)
                .seen(false)
                .sendTime(LocalDateTime.now())
                .build();

        return messagesRepository.save(message);
    }

    /**
     * Retrieve all messages for a chat
     */
    public Flux<Messages> getMessagesByChatId(String chatId) {
        return messagesRepository.findByChatId(chatId);
    }

    /**
     * Add a member to a chat
     */
    public Mono<Members> addMember(String chatId, Long userId, String role) {
        if (chatId == null || chatId.isBlank()) {
        return Mono.error(new IllegalArgumentException("Chat ID cannot be null or empty"));
        }

        if (userId == null) {
            return Mono.error(new IllegalArgumentException("User ID cannot be null"));
        }

        return membersRepository.existsById(UUID.randomUUID())  // ✅ Ensure no duplicate membership
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalStateException("User is already a member of the chat."));
                    }

                    UUID memberId = UUID.randomUUID();
                    return membersRepository.insertMember(memberId, chatId, userId, role, LocalDateTime.now());
                })
                .doOnSuccess(member -> System.out.println("Member added: " + member.getUserId()))
                .doOnError(Throwable::printStackTrace);
    }

    /**
     * Retrieve all members of a chat
     */
    public Flux<Members> getMembersByChatId(String chatId) {
        return membersRepository.findByChatId(chatId);
    }
}
