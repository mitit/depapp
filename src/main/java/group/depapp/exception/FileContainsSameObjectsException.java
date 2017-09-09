package group.depapp.exception;


/**
 * Класс, описывающий исключение при присутствии в файле двух одинаковых объектов
 */
public class FileContainsSameObjectsException extends Exception {

    final static String fileContainsSameObjects = "В файле присутствуют одинаковые объекты";

    public FileContainsSameObjectsException() {
        super(fileContainsSameObjects);
    }
}
