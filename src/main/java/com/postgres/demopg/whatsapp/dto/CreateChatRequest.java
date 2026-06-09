package com.postgres.demopg.whatsapp.dto;

public class CreateChatRequest {

    private String contactUsername;

    public CreateChatRequest() {
    }

    public String getContactUsername() {
        return contactUsername;
    }

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }
}