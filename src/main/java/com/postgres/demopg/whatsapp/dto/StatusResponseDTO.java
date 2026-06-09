package com.postgres.demopg.whatsapp.dto;

import com.postgres.demopg.models.User;
import com.postgres.demopg.whatsapp.entity.WhatsAppStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatusResponseDTO {

    private Long id;
    private String name;
    private String avatar;
    private String time;
    private boolean viewed;
    private String content;
    private String imageBase64;
    private String imageMimeType;

    private Long ownerId;
    private String ownerUsername;
    private boolean mine;

    private String ownerProfileImageBase64;
    private String ownerProfileImageMimeType;

    private int viewersCount;
    private List<StatusViewerDTO> viewers = new ArrayList<>();

    public StatusResponseDTO(WhatsAppStatus status, User currentUser) {
        this.id = status.getId();
        this.time = status.getTime();
        this.content = status.getContent();
        this.imageBase64 = status.getImageBase64();
        this.imageMimeType = status.getImageMimeType();

        User owner = status.getOwner();

        if (owner != null) {
            this.ownerId = owner.getId();
            this.ownerUsername = owner.getUsername();
            this.name = owner.getName();
            this.avatar = owner.getAvatar();
            this.ownerProfileImageBase64 = owner.getProfileImageBase64();
            this.ownerProfileImageMimeType = owner.getProfileImageMimeType();
            this.mine = currentUser != null && owner.getId().equals(currentUser.getId());
        } else {
            this.name = status.getName();
            this.avatar = status.getAvatar();
            this.mine = false;
        }

        if (currentUser != null && !this.mine) {
            this.viewed = status.hasViewer(currentUser);
        } else {
            this.viewed = false;
        }

        this.viewersCount = status.getViewers().size();

        if (this.mine) {
            this.viewers = status.getViewers()
                    .stream()
                    .map(StatusViewerDTO::new)
                    .collect(Collectors.toList());
        }
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

    public boolean isViewed() {
        return viewed;
    }

    public String getContent() {
        return content;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public boolean isMine() {
        return mine;
    }

    public String getOwnerProfileImageBase64() {
        return ownerProfileImageBase64;
    }

    public String getOwnerProfileImageMimeType() {
        return ownerProfileImageMimeType;
    }

    public int getViewersCount() {
        return viewersCount;
    }

    public List<StatusViewerDTO> getViewers() {
        return viewers;
    }
}