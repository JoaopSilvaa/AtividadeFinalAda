package br.ada.schedule.task.exception;

import br.ada.schedule.common.I18nKey;

@I18nKey("task.exception.already-close")
public class TaskAlreadyClosedException extends RuntimeException{

    public TaskAlreadyClosedException() {
        super("Task already close");
    }
}
