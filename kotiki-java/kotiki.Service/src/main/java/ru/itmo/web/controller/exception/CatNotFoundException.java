package ru.itmo.web.controller.exception;

public class CatNotFoundException extends RuntimeException {

    public CatNotFoundException() {
        super();
    }

    public CatNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CatNotFoundException(final String message) {
        super(message);
    }

    public CatNotFoundException(final Throwable cause) {
        super(cause);
    }
}