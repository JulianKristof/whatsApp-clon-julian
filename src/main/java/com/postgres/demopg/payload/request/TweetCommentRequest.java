package com.postgres.demopg.payload.request;

import jakarta.validation.constraints.NotBlank;

public class TweetCommentRequest {

    @NotBlank
    private String contenido;

    public TweetCommentRequest() {
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}