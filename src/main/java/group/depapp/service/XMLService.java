package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.exception.FieldTooLongException;
import group.depapp.exception.FileContainsSameObjectsException;

import java.util.List;

/**
 * Сервис для работы с XML файлом
 */
public interface XMLService {

    /**
     * Метод для генерации XML файла из содержимого таблицы
     * @param pathname путь, начиная из корня проекта, для создания файла
     */
    void create(String pathname);

    /**
     * Метод для выгрузки данных из XML файла
     * @param pathname путь, начиная из корня проекта, по кторому нужно найти файл для обработки
     * @return список данных из файла
     * @throws FileContainsSameObjectsException исключение, которое выбрасывается в случае, если файл содержит одинаковые объекты
     * @throws FieldTooLongException  исключение, которое выбрасывается в случае, если поле объекта из файла слишком длинное
     */
    List<Department> loadData(String pathname) throws FileContainsSameObjectsException, FieldTooLongException;
}
