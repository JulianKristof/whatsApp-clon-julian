package com.postgres.demopg.whatsapp.entity;

import com.postgres.demopg.models.User;
import jakarta.persistence.*;

@Entity
@Table(name = "whatsapp_messages")
public class WhatsAppMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String text;

    private String time;

    private boolean read;

    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    private String imageMimeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private WhatsAppChat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    public WhatsAppMessage() {
    }

    public WhatsAppMessage(String text, String time, boolean read) {
        this.text = text;
        this.time = time;
        this.read = read;
    }

    public WhatsAppMessage(
            String text,
            String time,
            boolean read,
            String imageBase64,
            String imageMimeType
    ) {
        this.text = text;
        this.time = time;
        this.read = read;
        this.imageBase64 = imageBase64;
        this.imageMimeType = imageMimeType;
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

    public boolean isRead() {
        return read;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }

    public WhatsAppChat getChat() {
        return chat;
    }

    public User getSender() {
        return sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }

    public void setChat(WhatsAppChat chat) {
        this.chat = chat;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}