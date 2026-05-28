package com.postgres.demopg.whatsapp.controller;

import com.postgres.demopg.whatsapp.dto.CallMockDTO;
import com.postgres.demopg.whatsapp.dto.ChatMockDTO;
import com.postgres.demopg.whatsapp.dto.MessageMockDTO;
import com.postgres.demopg.whatsapp.dto.SendMessageRequest;
import com.postgres.demopg.whatsapp.dto.StatusMockDTO;
import com.postgres.demopg.whatsapp.service.WhatsAppMockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppMockController {

    private final WhatsAppMockService whatsAppMockService;

    public WhatsAppMockController(WhatsAppMockService whatsAppMockService) {
        this.whatsAppMockService = whatsAppMockService;
    }

    @GetMapping("/chats")
    public ArrayList<ChatMockDTO> getChats() {
        return whatsAppMockService.getChats();
    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<ChatMockDTO> getChatById(@PathVariable int id) {
        ChatMockDTO chat = whatsAppMockService.getChatById(id);

        if (chat == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(chat);
    }

    @GetMapping("/chats/{id}/messages")
    public ArrayList<MessageMockDTO> getMessagesByChatId(@PathVariable int id) {
        return whatsAppMockService.getMessagesByChatId(id);
    }

    @PostMapping("/chats/{id}/messages")
    public ResponseEntity<MessageMockDTO> sendMessage(
            @PathVariable int id,
            @RequestBody SendMessageRequest request
    ) {
        if (request == null || request.getText() == null || request.getText().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        MessageMockDTO newMessage = whatsAppMockService.sendMessage(id, request.getText().trim());

        if (newMessage == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(newMessage);
    }

    @DeleteMapping("/chats/{id}/messages")
    public ResponseEntity<Void> clearMessages(@PathVariable int id) {
        boolean deleted = whatsAppMockService.clearMessages(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statuses")
    public ArrayList<StatusMockDTO> getStatuses() {
        return whatsAppMockService.getStatuses();
    }

    @GetMapping("/calls")
    public ArrayList<CallMockDTO> getCalls() {
        return whatsAppMockService.getCalls();
    }
}