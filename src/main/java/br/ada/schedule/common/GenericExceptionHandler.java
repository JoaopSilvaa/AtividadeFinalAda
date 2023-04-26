package br.ada.schedule.common;

import br.ada.schedule.task.exception.TaskAlreadyClosedException;
import br.ada.schedule.user.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {

    private MessageSource messages;

    public GenericExceptionHandler(MessageSource messages) {
        this.messages = messages;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<Object> handleUserException(
            UserException ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        String key = I18nUtils.recoveryKey(ex.getClass());
        var body = Map.of(
                "code", HttpStatus.BAD_REQUEST,
                "message", messages.getMessage(key, null, request.getLocale())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(value = {TaskAlreadyClosedException.class})
    public ResponseEntity<Object> handleTaskAlreadyClosed(
            TaskAlreadyClosedException ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        String key = I18nUtils.recoveryKey(ex.getClass());
        var body = Map.of(
                "code", HttpStatus.UNPROCESSABLE_ENTITY,
                "message", messages.getMessage(key, null, request.getLocale())
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        String key = I18nUtils.recoveryKey(ex.getClass());
        var body = Map.of(
                "code", HttpStatus.NOT_FOUND,
                "message", messages.getMessage(key, null, request.getLocale())
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        log.warn("Unhandled exception:", ex);
        var errors = new ArrayList<Map<String, String>>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(Map.of(error.getField(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(Map.of(error.getObjectName(), error.getDefaultMessage()));
        }

        var body = Map.of(
                "code", HttpStatus.BAD_REQUEST,
                "errors", errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }
}
