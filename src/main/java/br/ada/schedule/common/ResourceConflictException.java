package br.ada.schedule.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceConflictException() {
        super();
    }

    public ResourceConflictException(final String message) {
        super(message);
    }

    public ResourceConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }
}