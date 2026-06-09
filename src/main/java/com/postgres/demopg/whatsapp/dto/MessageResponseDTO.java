package com.postgres.demopg.whatsapp.dto;

import com.postgres.demopg.models.User;
import com.postgres.demopg.whatsapp.entity.WhatsAppMessage;

public class MessageResponseDTO {

    private Long id;
    private String text;
    private String time;
    private boolean me;
    private boolean read;

    private Long senderId;
    private String senderName;
    private String senderAvatar;

    private String imageBase64;
    private String imageMimeType;

    public MessageResponseDTO(WhatsAppMessage message, Long currentUserId) {
        this.id = message.getId();
        this.text = message.getText();
        this.time = message.getTime();
        this.read = message.isRead();
        this.imageBase64 = message.getImageBase64();
        this.imageMimeType = message.getImageMimeType();

        User sender = message.getSender();

        if (sender != null) {
            this.senderId = sender.getId();
            this.senderName = sender.getName();
            this.senderAvatar = sender.getAvatar();
            this.me = sender.getId().equals(currentUserId);
        } else {
            this.me = false;
        }
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public boolean isMe() {
        return me;
    }

    public boolean isRead() {
        return read;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }
}