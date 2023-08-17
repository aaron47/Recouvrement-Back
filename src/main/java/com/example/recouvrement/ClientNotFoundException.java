package com.example.recouvrement;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientNotFoundException extends ResponseStatusException {
    public ClientNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
