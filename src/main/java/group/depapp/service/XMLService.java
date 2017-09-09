package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.exception.FieldTooLongException;
import group.depapp.exception.FileContainsSameObjectsException;

import java.util.List;

public interface XMLService {
    void create(String pathname);

    List<Department> loadData(String pathname) throws FileContainsSameObjectsException, FieldTooLongException;
}
