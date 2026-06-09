package com.postgres.demopg.whatsapp.dto;

import com.postgres.demopg.models.User;

public class StatusViewerDTO {

    private Long id;
    private String username;
    private String name;
    private String avatar;
    private String profileImageBase64;
    private String profileImageMimeType;

    public StatusViewerDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.avatar = user.getAvatar();
        this.profileImageBase64 = user.getProfileImageBase64();
        this.profileImageMimeType = user.getProfileImageMimeType();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getProfileImageBase64() {
        return profileImageBase64;
    }

    public String getProfileImageMimeType() {
        return profileImageMimeType;
    }
}