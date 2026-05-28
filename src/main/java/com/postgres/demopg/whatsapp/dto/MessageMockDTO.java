package com.postgres.demopg.whatsapp.dto;

public class MessageMockDTO {

    private String text;
    private String time;
    private boolean isMe;
    private boolean isRead;

    public MessageMockDTO() {
    }

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

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}