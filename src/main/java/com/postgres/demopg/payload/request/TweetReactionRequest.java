package com.postgres.demopg.payload.request;

import jakarta.validation.constraints.NotBlank;

public class TweetReactionRequest {

    @NotBlank
    private String reactionType;

    public TweetReactionRequest() {
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }
}