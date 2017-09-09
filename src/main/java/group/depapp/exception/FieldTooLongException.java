package group.depapp.exception;

public class FieldTooLongException extends Exception {

    final static String fieldTooLong = "Поле слишком длинное";

    public FieldTooLongException() {
        super(fieldTooLong);
    }
}
