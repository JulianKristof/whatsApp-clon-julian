package com.postgres.demopg.whatsapp.service;

import com.postgres.demopg.models.User;
import com.postgres.demopg.repository.UserRepository;
import com.postgres.demopg.security.services.UserDetailsImpl;
import com.postgres.demopg.whatsapp.dto.CallResponseDTO;
import com.postgres.demopg.whatsapp.dto.ChatResponseDTO;
import com.postgres.demopg.whatsapp.dto.CreateCallRequest;
import com.postgres.demopg.whatsapp.dto.CreateChatRequest;
import com.postgres.demopg.whatsapp.dto.CreateStatusRequest;
import com.postgres.demopg.whatsapp.dto.MessageResponseDTO;
import com.postgres.demopg.whatsapp.dto.StatusResponseDTO;
import com.postgres.demopg.whatsapp.entity.WhatsAppCall;
import com.postgres.demopg.whatsapp.entity.WhatsAppChat;
import com.postgres.demopg.whatsapp.entity.WhatsAppMessage;
import com.postgres.demopg.whatsapp.entity.WhatsAppStatus;
import com.postgres.demopg.whatsapp.repository.WhatsAppCallRepository;
import com.postgres.demopg.whatsapp.repository.WhatsAppChatRepository;
import com.postgres.demopg.whatsapp.repository.WhatsAppMessageRepository;
import com.postgres.demopg.whatsapp.repository.WhatsAppStatusRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WhatsAppService {

    private final UserRepository userRepository;
    private final WhatsAppChatRepository chatRepository;
    private final WhatsAppMessageRepository messageRepository;
    private final WhatsAppStatusRepository statusRepository;
    private final WhatsAppCallRepository callRepository;

    public WhatsAppService(
            UserRepository userRepository,
            WhatsAppChatRepository chatRepository,
            WhatsAppMessageRepository messageRepository,
            WhatsAppStatusRepository statusRepository,
            WhatsAppCallRepository callRepository
    ) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.statusRepository = statusRepository;
        this.callRepository = callRepository;
    }

    private User getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            throw new RuntimeException("Usuario no autenticado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private void validateChatMember(WhatsAppChat chat, User user) {
        boolean isMember = chat.getMembers()
                .stream()
                .anyMatch(member -> member.getId().equals(user.getId()));

        if (!isMember) {
            throw new RuntimeException("No tienes acceso a este chat");
        }
    }

    // =========================
    // CHATS
    // =========================

    @Transactional
    public List<ChatResponseDTO> getChats(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        return chatRepository.findByMembers_Id(currentUser.getId())
                .stream()
                .map(chat -> new ChatResponseDTO(chat, currentUser.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatResponseDTO getChatById(Long id, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        WhatsAppChat chat = chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        validateChatMember(chat, currentUser);

        return new ChatResponseDTO(chat, currentUser.getId());
    }

    @Transactional
    public ChatResponseDTO createChat(CreateChatRequest request, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        if (request.getContactUsername() == null || request.getContactUsername().trim().isEmpty()) {
            throw new RuntimeException("Debes enviar el username del contacto");
        }

        User contact = userRepository.findByUsername(request.getContactUsername().trim())
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado"));

        if (contact.getId().equals(currentUser.getId())) {
            throw new RuntimeException("No puedes crear un chat contigo mismo");
        }

        List<WhatsAppChat> currentUserChats = chatRepository.findByMembers_Id(currentUser.getId());

        for (WhatsAppChat chat : currentUserChats) {
            boolean hasContact = chat.getMembers()
                    .stream()
                    .anyMatch(member -> member.getId().equals(contact.getId()));

            if (!chat.isGroup() && hasContact && chat.getMembers().size() == 2) {
                return new ChatResponseDTO(chat, currentUser.getId());
            }
        }

        WhatsAppChat chat = new WhatsAppChat(
                contact.getName(),
                contact.getAvatar(),
                "Chat creado",
                getCurrentTime(),
                false
        );

        chat.addMember(currentUser);
        chat.addMember(contact);

        WhatsAppChat savedChat = chatRepository.save(chat);

        return new ChatResponseDTO(savedChat, currentUser.getId());
    }

    @Transactional
    public void deleteChat(Long chatId, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        WhatsAppChat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        validateChatMember(chat, currentUser);

        chatRepository.delete(chat);
    }

    @Transactional
    public List<MessageResponseDTO> getMessagesByChatId(Long chatId, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        WhatsAppChat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        validateChatMember(chat, currentUser);

        return messageRepository.findByChatIdOrderByIdAsc(chatId)
                .stream()
                .map(message -> new MessageResponseDTO(message, currentUser.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
public MessageResponseDTO sendMessage(
        Long chatId,
        String text,
        String imageBase64,
        String imageMimeType,
        Authentication authentication
) {
    User currentUser = getCurrentUser(authentication);

    WhatsAppChat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

    validateChatMember(chat, currentUser);

    String cleanText = text == null ? "" : text.trim();

    boolean hasText = !cleanText.isEmpty();
    boolean hasImage = imageBase64 != null && !imageBase64.trim().isEmpty();

    if (!hasText && !hasImage) {
        throw new RuntimeException("El mensaje debe tener texto o imagen");
    }

    String time = getCurrentTime();

    WhatsAppMessage message = new WhatsAppMessage(
            cleanText,
            time,
            false,
            imageBase64,
            imageMimeType
    );

    message.setChat(chat);
    message.setSender(currentUser);

    WhatsAppMessage savedMessage = messageRepository.save(message);

    if (hasImage && !hasText) {
        chat.setLastMessage("📷 Imagen");
    } else if (hasImage) {
        chat.setLastMessage("📷 " + cleanText);
    } else {
        chat.setLastMessage(cleanText);
    }

    chat.setTime(time);

    chatRepository.save(chat);

    return new MessageResponseDTO(savedMessage, currentUser.getId());
}

    @Transactional
    public void markChatAsRead(Long chatId, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        WhatsAppChat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        validateChatMember(chat, currentUser);

        List<WhatsAppMessage> messages = messageRepository.findByChatId(chatId);

        for (WhatsAppMessage message : messages) {
            if (message.getSender() != null
                    && !message.getSender().getId().equals(currentUser.getId())) {
                message.setRead(true);
            }
        }

        messageRepository.saveAll(messages);
    }

    @Transactional
    public void clearMessages(Long chatId, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        WhatsAppChat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        validateChatMember(chat, currentUser);

        List<WhatsAppMessage> messages = messageRepository.findByChatId(chatId);
        messageRepository.deleteAll(messages);

        chat.setLastMessage("Chat vacío");
        chat.setTime(getCurrentTime());

        chatRepository.save(chat);
    }

    // =========================
    // ESTADOS
    // =========================

    @Transactional
    public List<StatusResponseDTO> getStatuses(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        return statusRepository.findAll()
                .stream()
                .map(status -> new StatusResponseDTO(status, currentUser))
                .collect(Collectors.toList());
    }

    @Transactional
    public StatusResponseDTO createStatus(CreateStatusRequest request, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        String content = request.getContent() == null
                ? ""
                : request.getContent().trim();

        String imageBase64 = request.getImageBase64();
        String imageMimeType = request.getImageMimeType();

        boolean hasText = !content.isEmpty();
        boolean hasImage = imageBase64 != null && !imageBase64.trim().isEmpty();

        if (!hasText && !hasImage) {
            throw new RuntimeException("El estado debe tener texto o imagen");
        }

        WhatsAppStatus status = new WhatsAppStatus(
                currentUser.getName(),
                currentUser.getAvatar(),
                getCurrentTime(),
                content,
                imageBase64,
                imageMimeType,
                currentUser
        );

        WhatsAppStatus savedStatus = statusRepository.save(status);

        return new StatusResponseDTO(savedStatus, currentUser);
    }

    @Transactional
    public StatusResponseDTO markStatusAsViewed(Long id, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        WhatsAppStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        if (!status.isOwner(currentUser)) {
            status.addViewer(currentUser);
            statusRepository.save(status);
        }

        return new StatusResponseDTO(status, currentUser);
    }

    @Transactional
    public void deleteStatus(Long id, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        WhatsAppStatus status = statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        if (!status.isOwner(currentUser)) {
            throw new RuntimeException("Solo el dueño puede eliminar este estado");
        }

        statusRepository.delete(status);
    }

    // =========================
    // LLAMADAS
    // =========================

    public List<CallResponseDTO> getCalls() {
        return callRepository.findAll()
                .stream()
                .map(CallResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CallResponseDTO createCall(CreateCallRequest request) {
        String name = request.getName() == null || request.getName().trim().isEmpty()
                ? "Desconocido"
                : request.getName().trim();

        String avatar = request.getAvatar() == null || request.getAvatar().trim().isEmpty()
                ? "?"
                : request.getAvatar().trim();

        String type = request.getType() == null || request.getType().trim().isEmpty()
                ? "outgoing"
                : request.getType().trim();

        WhatsAppCall call = new WhatsAppCall(
                name,
                avatar,
                getCurrentTime(),
                type,
                request.isVideoCall()
        );

        WhatsAppCall savedCall = callRepository.save(call);

        return new CallResponseDTO(savedCall);
    }

    @Transactional
    public void deleteCall(Long id) {
        if (!callRepository.existsById(id)) {
            throw new RuntimeException("Llamada no encontrada");
        }

        callRepository.deleteById(id);
    }

    // =========================
    // UTILIDAD
    // =========================

    private String getCurrentTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm");

        String hourMinute = now.format(formatter);
        String period = now.getHour() < 12 ? "a. m." : "p. m.";

        return hourMinute + " " + period;
    }
}