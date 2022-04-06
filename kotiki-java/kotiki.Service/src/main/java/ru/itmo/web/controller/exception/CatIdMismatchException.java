package ru.itmo.web.controller.exception;

public class CatIdMismatchException extends RuntimeException {

    public CatIdMismatchException() {
        super();
    }

    public CatIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CatIdMismatchException(final String message) {
        super(message);
    }

    public CatIdMismatchException(final Throwable cause) {
        super(cause);
    }
}