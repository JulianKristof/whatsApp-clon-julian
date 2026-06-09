package com.postgres.demopg.payload.response;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String name;
    private String avatar;
    private boolean online;
    private String lastSeen;

    public JwtResponse(
            String token,
            Long id,
            String username,
            String email,
            String name,
            String avatar,
            boolean online,
            String lastSeen
    ) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
        this.lastSeen = lastSeen;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
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
}