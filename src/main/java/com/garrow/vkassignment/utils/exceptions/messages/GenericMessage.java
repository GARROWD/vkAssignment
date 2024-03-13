package com.garrow.vkassignment.utils.exceptions.messages;

import lombok.Data;

@Data
public class GenericMessage {
    private String message;

    private String details;

    public GenericMessage(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public GenericMessage(String message) {
        this.message = message;
        this.details = null;
    }
}
