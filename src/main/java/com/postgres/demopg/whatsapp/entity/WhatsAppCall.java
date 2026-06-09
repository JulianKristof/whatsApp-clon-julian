package com.postgres.demopg.whatsapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "whatsapp_calls")
public class WhatsAppCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String avatar;
    private String time;
    private String type;
    private boolean isVideoCall;

    public WhatsAppCall() {
    }

    public WhatsAppCall(String name, String avatar, String time, String type, boolean isVideoCall) {
        this.name = name;
        this.avatar = avatar;
        this.time = time;
        this.type = type;
        this.isVideoCall = isVideoCall;
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

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public boolean isVideoCall() {
        return isVideoCall;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVideoCall(boolean videoCall) {
        isVideoCall = videoCall;
    }
}