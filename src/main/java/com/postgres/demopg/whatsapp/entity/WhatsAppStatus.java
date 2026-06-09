package com.postgres.demopg.whatsapp.entity;

import com.postgres.demopg.models.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "whatsapp_statuses")
public class WhatsAppStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String avatar;
    private String time;

    @Column(length = 2000)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    private String imageMimeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "whatsapp_status_viewers",
            joinColumns = @JoinColumn(name = "status_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> viewers = new ArrayList<>();

    public WhatsAppStatus() {
    }

    public WhatsAppStatus(
            String name,
            String avatar,
            String time,
            String content,
            String imageBase64,
            String imageMimeType,
            User owner
    ) {
        this.name = name;
        this.avatar = avatar;
        this.time = time;
        this.content = content;
        this.imageBase64 = imageBase64;
        this.imageMimeType = imageMimeType;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        if (owner != null) {
            return owner.getName();
        }
        return name;
    }

    public String getAvatar() {
        if (owner != null) {
            return owner.getAvatar();
        }
        return avatar;
    }

    public String getTime() {
        return time;
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

    public User getOwner() {
        return owner;
    }

    public List<User> getViewers() {
        return viewers;
    }

    public void addViewer(User user) {
        boolean alreadyViewed = viewers
                .stream()
                .anyMatch(viewer -> viewer.getId().equals(user.getId()));

        if (!alreadyViewed) {
            viewers.add(user);
        }
    }

    public boolean hasViewer(User user) {
        return viewers
                .stream()
                .anyMatch(viewer -> viewer.getId().equals(user.getId()));
    }

    public boolean isOwner(User user) {
        return owner != null && user != null && owner.getId().equals(user.getId());
    }
}