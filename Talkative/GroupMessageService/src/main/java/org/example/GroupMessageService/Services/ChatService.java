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
import java.util.logging.Logger;

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
    return chatRepo.findByChatId(chatId)// Ensure chat exists first
        .switchIfEmpty(Mono.error(new RuntimeException("Chat ID does not exist.")))
        .flatMap(chat -> {
            Messages message = Messages.builder()
                .messageId(null)  // Ensure new UUID
                .chatId(chatId)
                .senderId(senderId)
                .message(messageText)
                .seen(false)
                .sendTime(LocalDateTime.now())
                .build();

            System.out.println("System Check: " + message.getMessageId());
            return messagesRepository.save(message);
        });
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
    public Mono<String> addMember(String chatId, Long userId, String role) {
        if (chatId == null || chatId.isBlank()) {
        return Mono.error(new IllegalArgumentException("Chat ID cannot be null or empty"));
        }

        if (userId == null) {
            return Mono.error(new IllegalArgumentException("User ID cannot be null"));
        }

        return membersRepository.save(Members.builder()
                                .id(null) // Ensure unique ID
                                .chatId(chatId)
                                .userId(userId) // ✅ Add userId here
                                .role(role)
                                .joinedAt(LocalDateTime.now())
                                .build())
                                .doOnSuccess(member -> System.out.println("Member added: " + member.getUserId()))
                                .thenReturn("Member successfully added") // ✅ Return success message
                                .doOnError(e -> {
                                    System.out.println("User Already Exist");
                                });

    }

    /**
     * Retrieve all members of a chat
     */
    public Flux<Members> getMembersByChatId(String chatId) {
        return membersRepository.findByChatId(chatId);
    }
}
