package org.example.MessagingService.Repository;

import org.example.MessagingService.Models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepo extends JpaRepository<Contact,Long> {

   List<Contact> findByUserId(Long userId);

    @Query("SELECT c FROM Contact c WHERE c.userId = :userId AND c.contactUserId = :contactUserId")
    Optional<Contact> findByUserIdAndContactUserId(@Param("userId") Long userId, @Param("contactUserId") Long contactUserId);
}
