package br.ada.schedule.user.exception;

import br.ada.schedule.user.User;

public abstract class UserException extends Exception {

    private User rejectObject;
    private String rejectField;
    private Object rejectedValue;


    public UserException(String message, User rejectObject, String rejectField, Object rejectedValue) {
        super(message);
        this.rejectObject = rejectObject;
        this.rejectField = rejectField;
        this.rejectedValue = rejectedValue;
    }

    public User getRejectObject() {
        return rejectObject;
    }

    public String getRejectField() {
        return rejectField;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

}
