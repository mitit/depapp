package group.depapp.service;

import group.depapp.domain.Department;

import java.util.List;

/**
 * Сервис для работы с репозиторием, обрабатывающем запросы к таблице, хранящей данные для объектов Department
 */
public interface DepartmentService {

    /**
     * Сохранение списка объектов типа Department
     * @param departments список объектов Department
     * @return возврщающее значение = true, в случае если операция прошла успешно, false - если неуспешно
     */
    boolean save(List<Department> departments);

    /**
     * Получение всего списка объектов типа Department
     * @return список объектов
     */
    List<Department> getAll();

    /**
     * Удаление списка объектов типа Department
     * @param departments список объектов Department
     * @return возврщающее значение = true, в случае если операция прошла успешно, false - если неуспешно
     */
    boolean delete(List<Department> departments);

    /**
     * Редактирование объекта типа Department
     * @param department объект для редактирования
     * @return возврщающее значение = true, в случае если операция прошла успешно, false - если неуспешно
     */
    boolean update(Department department);
}
