package com.postgres.demopg.whatsapp.controller;

import com.postgres.demopg.whatsapp.dto.CallResponseDTO;
import com.postgres.demopg.whatsapp.dto.ChatResponseDTO;
import com.postgres.demopg.whatsapp.dto.CreateCallRequest;
import com.postgres.demopg.whatsapp.dto.CreateChatRequest;
import com.postgres.demopg.whatsapp.dto.CreateStatusRequest;
import com.postgres.demopg.whatsapp.dto.MessageResponseDTO;
import com.postgres.demopg.whatsapp.dto.SendMessageRequest;
import com.postgres.demopg.whatsapp.dto.StatusResponseDTO;
import com.postgres.demopg.whatsapp.service.WhatsAppService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    // =========================
    // CHATS
    // =========================

    @GetMapping("/chats")
    public List<ChatResponseDTO> getChats(Authentication authentication) {
        return whatsAppService.getChats(authentication);
    }

    @PostMapping("/chats")
    public ResponseEntity<ChatResponseDTO> createChat(
            @RequestBody CreateChatRequest request,
            Authentication authentication
    ) {
        ChatResponseDTO chat = whatsAppService.createChat(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(chat);
    }

    @GetMapping("/chats/{id}")
    public ChatResponseDTO getChatById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return whatsAppService.getChatById(id, authentication);
    }

    @DeleteMapping("/chats/{id}")
    public ResponseEntity<Void> deleteChat(
            @PathVariable Long id,
            Authentication authentication
    ) {
        whatsAppService.deleteChat(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/chats/{id}/messages")
    public List<MessageResponseDTO> getMessagesByChatId(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return whatsAppService.getMessagesByChatId(id, authentication);
    }

   @PostMapping("/chats/{id}/messages")
public ResponseEntity<MessageResponseDTO> sendMessage(
        @PathVariable Long id,
        @RequestBody SendMessageRequest request,
        Authentication authentication
) {
    if (request == null) {
        return ResponseEntity.badRequest().build();
    }

    boolean hasText = request.getText() != null && !request.getText().trim().isEmpty();
    boolean hasImage = request.getImageBase64() != null && !request.getImageBase64().trim().isEmpty();

    if (!hasText && !hasImage) {
        return ResponseEntity.badRequest().build();
    }

    MessageResponseDTO message = whatsAppService.sendMessage(
            id,
            request.getText(),
            request.getImageBase64(),
            request.getImageMimeType(),
            authentication
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(message);
}

    @PutMapping("/chats/{id}/read")
    public ResponseEntity<Void> markChatAsRead(
            @PathVariable Long id,
            Authentication authentication
    ) {
        whatsAppService.markChatAsRead(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/chats/{id}/messages")
    public ResponseEntity<Void> clearMessages(
            @PathVariable Long id,
            Authentication authentication
    ) {
        whatsAppService.clearMessages(id, authentication);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // ESTADOS
    // =========================

    @GetMapping("/statuses")
    public List<StatusResponseDTO> getStatuses(Authentication authentication) {
        return whatsAppService.getStatuses(authentication);
    }

    @PostMapping("/statuses")
    public ResponseEntity<StatusResponseDTO> createStatus(
            @RequestBody CreateStatusRequest request,
            Authentication authentication
    ) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        StatusResponseDTO status = whatsAppService.createStatus(request, authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @PutMapping("/statuses/{id}/viewed")
    public StatusResponseDTO markStatusAsViewed(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return whatsAppService.markStatusAsViewed(id, authentication);
    }

    @DeleteMapping("/statuses/{id}")
    public ResponseEntity<Void> deleteStatus(
            @PathVariable Long id,
            Authentication authentication
    ) {
        whatsAppService.deleteStatus(id, authentication);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // LLAMADAS
    // =========================

    @GetMapping("/calls")
    public List<CallResponseDTO> getCalls() {
        return whatsAppService.getCalls();
    }

    @PostMapping("/calls")
    public ResponseEntity<CallResponseDTO> createCall(
            @RequestBody CreateCallRequest request
    ) {
        CallResponseDTO call = whatsAppService.createCall(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(call);
    }

    @DeleteMapping("/calls/{id}")
    public ResponseEntity<Void> deleteCall(@PathVariable Long id) {
        whatsAppService.deleteCall(id);
        return ResponseEntity.noContent().build();
    }
}