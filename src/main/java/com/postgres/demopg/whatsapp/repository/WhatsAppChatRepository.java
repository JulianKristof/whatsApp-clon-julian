package com.postgres.demopg.whatsapp.repository;

import com.postgres.demopg.whatsapp.entity.WhatsAppChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhatsAppChatRepository extends JpaRepository<WhatsAppChat, Long> {

    List<WhatsAppChat> findByMembers_Id(Long userId);
}