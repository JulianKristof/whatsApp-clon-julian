package com.postgres.demopg.whatsapp.dto;

import java.util.ArrayList;

public class ChatMockDTO {

    private int id;
    private String name;
    private String avatar;
    private String lastMessage;
    private String time;
    private int unreadMessages;
    private boolean isPinned;
    private boolean isMuted;
    private ArrayList<MessageMockDTO> messages;

    public ChatMockDTO() {
    }

    public ChatMockDTO(
            int id,
            String name,
            String avatar,
            String lastMessage,
            String time,
            int unreadMessages,
            boolean isPinned,
            boolean isMuted,
            ArrayList<MessageMockDTO> messages
    ) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.lastMessage = lastMessage;
        this.time = time;
        this.unreadMessages = unreadMessages;
        this.isPinned = isPinned;
        this.isMuted = isMuted;
        this.messages = messages;
    }

    public int getId() {
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

    public ArrayList<MessageMockDTO> getMessages() {
        return messages;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public void setMessages(ArrayList<MessageMockDTO> messages) {
        this.messages = messages;
    }
}