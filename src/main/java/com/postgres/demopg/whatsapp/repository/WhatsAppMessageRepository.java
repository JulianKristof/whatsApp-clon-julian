package com.postgres.demopg.whatsapp.repository;

import com.postgres.demopg.whatsapp.entity.WhatsAppMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhatsAppMessageRepository extends JpaRepository<WhatsAppMessage, Long> {

    List<WhatsAppMessage> findByChatIdOrderByIdAsc(Long chatId);

    List<WhatsAppMessage> findByChatId(Long chatId);
}