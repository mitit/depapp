package group.depapp.service;

import group.depapp.domain.Department;

import java.util.List;

public interface DepartmentService {

    boolean save(List<Department> departments);

    List<Department> getAll();

    boolean delete(List<Department> departments);

    boolean update(Department department);
}
