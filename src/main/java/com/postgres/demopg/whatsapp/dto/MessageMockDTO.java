package com.postgres.demopg.whatsapp.dto;

public class MessageMockDTO {

    private String text;
    private String time;
    private boolean isMe;
    private boolean isRead;

    public MessageMockDTO(String text, String time, boolean isMe, boolean isRead) {
        this.text = text;
        this.time = time;
        this.isMe = isMe;
        this.isRead = isRead;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public boolean isMe() {
        return isMe;
    }

    public boolean isRead() {
        return isRead;
    }
}