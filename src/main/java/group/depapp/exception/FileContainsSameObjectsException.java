package group.depapp.exception;

public class FileContainsSameObjectsException extends Exception {

    final static String fileContainsSameObjects = "В файле присутствуют одинаковые объекты";

    public FileContainsSameObjectsException() {
        super(fileContainsSameObjects);
    }
}
