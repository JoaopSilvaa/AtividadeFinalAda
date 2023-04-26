package br.ada.schedule.user.exception;

import br.ada.schedule.common.I18nKey;
import br.ada.schedule.user.User;

@I18nKey("user.exception.username-in-use")
public class UsernameInUseException extends UserException {


    public UsernameInUseException(
            User rejectObject,
            Object rejectedValue
    ) {
        super("Username is already in use", rejectObject, "username", rejectedValue);
    }
}
