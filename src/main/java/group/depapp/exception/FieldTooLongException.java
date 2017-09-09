package group.depapp.exception;

/**
 * Класс, описывающий исключение при превышении допустимой длины поля объекта
 */
public class FieldTooLongException extends Exception {

    final static String fieldTooLong = "Поле объекта слишком длинное";

    public FieldTooLongException() {
        super(fieldTooLong);
    }
}
