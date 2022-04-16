package ru.itmo.web.controller.exception;

public class OwnerIdMissmatchException extends RuntimeException {

    public OwnerIdMissmatchException() {
        super();
    }

    public OwnerIdMissmatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OwnerIdMissmatchException(final String message) {
        super(message);
    }

    public OwnerIdMissmatchException(final Throwable cause) {
        super(cause);
    }
}