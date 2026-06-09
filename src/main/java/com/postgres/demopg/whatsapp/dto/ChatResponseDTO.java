package com.postgres.demopg.whatsapp.dto;

import com.postgres.demopg.models.User;
import com.postgres.demopg.whatsapp.entity.WhatsAppChat;
import com.postgres.demopg.whatsapp.entity.WhatsAppMessage;

import java.util.List;
import java.util.stream.Collectors;

public class ChatResponseDTO {

    private Long id;
    private String name;
    private String avatar;
    private String lastMessage;
    private String time;
    private int unreadMessages;
    private boolean isPinned;
    private boolean isMuted;
    private boolean isGroup;

    private boolean contactOnline;
    private String contactLastSeen;

    private String contactProfileImageBase64;
    private String contactProfileImageMimeType;

    private List<MessageResponseDTO> messages;

    public ChatResponseDTO(WhatsAppChat chat, Long currentUserId) {
        this.id = chat.getId();
        this.isGroup = chat.isGroup();

        User displayUser = getDisplayUser(chat, currentUserId);

        if (chat.isGroup()) {
            this.name = chat.getName();
            this.avatar = chat.getAvatar();
            this.contactOnline = false;
            this.contactLastSeen = "";
            this.contactProfileImageBase64 = null;
            this.contactProfileImageMimeType = null;
        } else if (displayUser != null) {
            this.name = displayUser.getName();
            this.avatar = displayUser.getAvatar();
            this.contactOnline = displayUser.isOnline();
            this.contactLastSeen = displayUser.getLastSeen();
            this.contactProfileImageBase64 = displayUser.getProfileImageBase64();
            this.contactProfileImageMimeType = displayUser.getProfileImageMimeType();
        } else {
            this.name = chat.getName();
            this.avatar = chat.getAvatar();
            this.contactOnline = false;
            this.contactLastSeen = "";
            this.contactProfileImageBase64 = null;
            this.contactProfileImageMimeType = null;
        }

        this.lastMessage = chat.getLastMessage();
        this.time = chat.getTime();
        this.unreadMessages = countUnreadMessages(chat, currentUserId);
        this.isPinned = false;
        this.isMuted = false;

        this.messages = chat.getMessages()
                .stream()
                .map(message -> new MessageResponseDTO(message, currentUserId))
                .collect(Collectors.toList());
    }

    private User getDisplayUser(WhatsAppChat chat, Long currentUserId) {
        for (User user : chat.getMembers()) {
            if (!user.getId().equals(currentUserId)) {
                return user;
            }
        }

        return null;
    }

    private int countUnreadMessages(WhatsAppChat chat, Long currentUserId) {
        int count = 0;

        for (WhatsAppMessage message : chat.getMessages()) {
            if (!message.isRead()
                    && message.getSender() != null
                    && !message.getSender().getId().equals(currentUserId)) {
                count++;
            }
        }

        return count;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTime() {
        return time;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public boolean isContactOnline() {
        return contactOnline;
    }

    public String getContactLastSeen() {
        return contactLastSeen;
    }

    public String getContactProfileImageBase64() {
        return contactProfileImageBase64;
    }

    public String getContactProfileImageMimeType() {
        return contactProfileImageMimeType;
    }

    public List<MessageResponseDTO> getMessages() {
        return messages;
    }
}