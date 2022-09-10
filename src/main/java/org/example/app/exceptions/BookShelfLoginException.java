package org.example.app.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookShelfLoginException extends Exception {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
