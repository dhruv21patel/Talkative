package org.example.MessagingService.Controller;

import org.example.MessagingService.Models.Contact;
import org.example.MessagingService.Models.Message;
import org.example.MessagingService.Repository.ContactRepo;
import org.example.MessagingService.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/Contact")
public class ContactController {

    @Autowired
    private ContactRepo contactRepository;

    @Autowired
    private MessageRepo messageRepository;

    // Fetch all contacts and their last messages for a user
    @GetMapping("/{userId}")
    public List<Contact> getContacts(@PathVariable Long userId) {
        return contactRepository.findByUserId(userId);
    }

}