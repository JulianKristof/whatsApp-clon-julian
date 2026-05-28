package com.postgres.demopg.payload.response;

import java.time.format.DateTimeFormatter;

import com.postgres.demopg.models.TweetComment;

public class TweetCommentResponseDTO {
    private Long id;
    private Long tweetId;
    private String contenido;
    private String username;
    private String createdAt;

    public TweetCommentResponseDTO(TweetComment comment) {
        this.id = comment.getId();
        this.tweetId = comment.getTweet() != null ? comment.getTweet().getId() : null;
        this.contenido = comment.getContenido();
        this.username = comment.getUser() != null ? comment.getUser().getUsername() : null;
        this.createdAt = comment.getCreatedAt() != null
                ? comment.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}