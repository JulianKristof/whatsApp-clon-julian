package com.postgres.demopg.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCallRequest {

    private String name;
    private String avatar;
    private String type;

    @JsonProperty("videoCall")
    @JsonAlias({"isVideoCall"})
    private boolean videoCall;

    public CreateCallRequest() {
    }

    public CreateCallRequest(String name, String avatar, String type, boolean videoCall) {
        this.name = name;
        this.avatar = avatar;
        this.type = type;
        this.videoCall = videoCall;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getType() {
        return type;
    }

    @JsonProperty("videoCall")
    public boolean isVideoCall() {
        return videoCall;
    }

    public boolean getVideoCall() {
        return videoCall;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("videoCall")
    @JsonAlias({"isVideoCall"})
    public void setVideoCall(boolean videoCall) {
        this.videoCall = videoCall;
    }

    @Override
    public String toString() {
        return "CreateCallRequest{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type='" + type + '\'' +
                ", videoCall=" + videoCall +
                '}';
    }
}