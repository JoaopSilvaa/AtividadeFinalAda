package br.ada.schedule.user.exception;

import br.ada.schedule.common.I18nKey;
import br.ada.schedule.user.User;

@I18nKey("user.exception.username-changed")
public class UsernameChangedException extends UserException {

    public UsernameChangedException(
            User rejectObject,
            Object rejectedValue
    ) {
        super("Username must not be change", rejectObject, "username", rejectedValue);
    }
}
