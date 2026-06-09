package com.postgres.demopg.whatsapp.repository;

import com.postgres.demopg.whatsapp.entity.WhatsAppCall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhatsAppCallRepository extends JpaRepository<WhatsAppCall, Long> {
}