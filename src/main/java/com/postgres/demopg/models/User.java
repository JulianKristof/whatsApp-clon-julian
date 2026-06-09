package com.postgres.demopg.models;

import jakarta.persistence.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String name;

    private String avatar;

    private boolean online;

    private String lastSeen;

    @Column(columnDefinition = "TEXT")
    private String profileImageBase64;

    private String profileImageMimeType;

    public User() {
    }

    public User(String username, String email, String password, String name, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.online = false;
        this.lastSeen = "Sin actividad reciente";
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

    public String getPassword() {
        return password;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setProfileImageBase64(String profileImageBase64) {
        this.profileImageBase64 = profileImageBase64;
    }

    public void setProfileImageMimeType(String profileImageMimeType) {
        this.profileImageMimeType = profileImageMimeType;
    }
}