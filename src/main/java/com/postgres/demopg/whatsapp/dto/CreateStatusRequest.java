package com.postgres.demopg.whatsapp.dto;

public class CreateStatusRequest {

    private String content;
    private String imageBase64;
    private String imageMimeType;

    public CreateStatusRequest() {
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }
}