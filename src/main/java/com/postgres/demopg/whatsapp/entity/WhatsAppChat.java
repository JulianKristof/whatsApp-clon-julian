package com.postgres.demopg.whatsapp.entity;

import com.postgres.demopg.models.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "whatsapp_chats")
public class WhatsAppChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String avatar;
    private String lastMessage;
    private String time;
    private boolean isGroup;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "whatsapp_chat_members",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @OneToMany(
            mappedBy = "chat",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<WhatsAppMessage> messages = new ArrayList<>();

    public WhatsAppChat() {
    }

    public WhatsAppChat(String name, String avatar, String lastMessage, String time, boolean isGroup) {
        this.name = name;
        this.avatar = avatar;
        this.lastMessage = lastMessage;
        this.time = time;
        this.isGroup = isGroup;
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

    public boolean isGroup() {
        return isGroup;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<WhatsAppMessage> getMessages() {
        return messages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void addMessage(WhatsAppMessage message) {
        messages.add(message);
        message.setChat(this);
    }

    public void clearMessages() {
        messages.clear();
    }
}