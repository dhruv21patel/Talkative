package org.example.MessagingService.Services;

import org.example.MessagingService.Models.Contact;
import org.example.MessagingService.Repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ContactService {

    @Autowired
    private ContactRepo contactRepo;

     public void updateContact(Long userId, Long contactUserId, String lastMessage) {
        Contact contact = contactRepo.findByUserIdAndContactUserId(userId, contactUserId)
                .orElse(
                        Contact.builder()
                                .userId(userId)
                                .contactUserId(contactUserId)
                                .lastMessage(lastMessage)
                                .lastInteraction( new Timestamp(System.currentTimeMillis()))
                                .isBlocked(false)
                                .createdAt( new Timestamp(System.currentTimeMillis() ))
                                .build());
        contact.setLastMessage(lastMessage);
        contact.setLastInteraction(new Timestamp(System.currentTimeMillis()));
        contactRepo.save(contact);
    }
}
