package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.exception.FieldTooLongException;
import group.depapp.exception.FileContainsSameObjectsException;

import java.util.List;

public interface XMLService {
    void create();

    List<Department> loadData() throws FileContainsSameObjectsException, FieldTooLongException;
}
