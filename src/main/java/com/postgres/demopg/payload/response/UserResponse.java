package com.postgres.demopg.payload.response;

import com.postgres.demopg.models.User;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private String avatar;
    private boolean online;
    private String lastSeen;
    private String profileImageBase64;
    private String profileImageMimeType;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.avatar = user.getAvatar();
        this.online = user.isOnline();
        this.lastSeen = user.getLastSeen();
        this.profileImageBase64 = user.getProfileImageBase64();
        this.profileImageMimeType = user.getProfileImageMimeType();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public String getProfileImageBase64() {
        return profileImageBase64;
    }

    public String getProfileImageMimeType() {
        return profileImageMimeType;
    }
}