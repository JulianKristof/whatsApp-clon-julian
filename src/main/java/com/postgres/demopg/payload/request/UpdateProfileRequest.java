package com.postgres.demopg.payload.request;

public class UpdateProfileRequest {

    private String name;
    private String avatar;
    private String profileImageBase64;
    private String profileImageMimeType;

    public UpdateProfileRequest() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setProfileImageBase64(String profileImageBase64) {
        this.profileImageBase64 = profileImageBase64;
    }

    public void setProfileImageMimeType(String profileImageMimeType) {
        this.profileImageMimeType = profileImageMimeType;
    }
}