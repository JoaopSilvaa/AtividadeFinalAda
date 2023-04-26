package br.ada.schedule.common;

public class I18nUtils {

    public static String recoveryKey(Class<? extends Exception> clazz) {
        I18nKey key = clazz.getDeclaredAnnotation(I18nKey.class);
        return key.value();
    }

}
