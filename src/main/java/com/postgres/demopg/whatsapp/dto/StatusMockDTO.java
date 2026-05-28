package com.postgres.demopg.whatsapp.dto;

public class StatusMockDTO {

    private String name;
    private String avatar;
    private String time;
    private boolean viewed;

    public StatusMockDTO(String name, String avatar, String time, boolean viewed) {
        this.name = name;
        this.avatar = avatar;
        this.time = time;
        this.viewed = viewed;
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

    public boolean isViewed() {
        return viewed;
    }
}