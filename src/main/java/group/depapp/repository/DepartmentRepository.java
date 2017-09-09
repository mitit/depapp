package group.depapp.repository;

import group.depapp.domain.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;

/**
 * Репозиторий для работы с таблицой, в которой хранятся объекты Department
 * @param <X>
 */
public interface DepartmentRepository<X extends Department> {

    /**
     * Вспомогательное поле для всех наследников данного репозитория, используется для метода getAll()
     */
    RowMapper<Department> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new Department(resultSet.getString("dep_code"),
                resultSet.getString("dep_job"),
                resultSet.getString("description"));
    };

    /**
     * Метод для сохранения одного объекта в базу данных
     * @param domain объект для сохранения
     */
    void save(X domain);

    /**
     * Метод для сохранения списка объектов в базу данных
     * @param domainList список объектов для сохранения
     */
    void save(List<X> domainList);

    /**
     * Метод для получения всех объектов из базы данных
     * @return лист всех объектов
     */
    List<X> getAll();

    /**
     * Метод для обновления строки в базе данных
     * @param domain объект, строку соответствующую которому нужно обновить
     */
    void update(X domain);

    /**
     * Метод для удаления одного объекта из базы данных
     * @param domain объект для удаления
     */
    void delete(X domain);

    /**
     * Метод для удаления списка объектов из базы данных
     * @param domainList список объектов для удаления
     */
    void delete(List<X> domainList);
}

