package com.postgres.demopg.whatsapp.dto;

public class CallMockDTO {

    private String name;
    private String avatar;
    private String time;
    private String type;
    private boolean isVideoCall;

    public CallMockDTO(String name, String avatar, String time, String type, boolean isVideoCall) {
        this.name = name;
        this.avatar = avatar;
        this.time = time;
        this.type = type;
        this.isVideoCall = isVideoCall;
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