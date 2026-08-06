package com.seeshow.backend.exception;

import jakarta.validation.constraints.Email;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
