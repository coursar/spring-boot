package com.example.springboot.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
    }

    public PostNotFoundException(final String message) {
        super(message);
    }

    public PostNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(final Throwable cause) {
        super(cause);
    }

    public PostNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
