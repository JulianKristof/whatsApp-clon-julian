package com.postgres.demopg.whatsapp.dto;

import com.postgres.demopg.whatsapp.entity.WhatsAppCall;

public class CallResponseDTO {

    private Long id;
    private String name;
    private String avatar;
    private String time;
    private String type;
    private boolean isVideoCall;

    public CallResponseDTO(WhatsAppCall call) {
        this.id = call.getId();
        this.name = call.getName();
        this.avatar = call.getAvatar();
        this.time = call.getTime();
        this.type = call.getType();
        this.isVideoCall = call.isVideoCall();
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
}