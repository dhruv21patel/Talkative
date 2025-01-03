package org.example.MessagingService.Controller;


import org.example.MessagingService.Models.Message;
import org.example.MessagingService.Repository.MessageRepo;
import org.example.MessagingService.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Message")
public class MessageController {

    @Autowired
    private MessageRepo messageRepository;

    @Autowired
    private ContactService contactService;

    // Get messages between two users
    @GetMapping("/between")
    public List<Message> getMessagesBetweenUsers(@RequestParam Long user1, @RequestParam Long user2) {
        return messageRepository.findMessagesBetweenUsers(user1, user2);
    }

    // Send a new message
    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
                // Save the message
        Message savedMessage = messageRepository.save(message);

        // Update sender's contact
        contactService.updateContact(message.getSenderId(), message.getReceiverId(), message.getContent());

        // Update receiver's contact
        contactService.updateContact(message.getReceiverId(), message.getSenderId(), message.getContent());

        return savedMessage;
    }

    // Mark a message as read
    @PutMapping("/mark-read/{id}")
    public Message markMessageAsRead(@PathVariable Long id) {
        Message message = messageRepository.findById(id).orElseThrow();
        message.setIsRead(true);
        return messageRepository.save(message);
    }


    // Delete a message
    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageRepository.deleteById(id);
    }
}
