package com.postgres.demopg.whatsapp.repository;

import com.postgres.demopg.whatsapp.entity.WhatsAppStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhatsAppStatusRepository extends JpaRepository<WhatsAppStatus, Long> {
}