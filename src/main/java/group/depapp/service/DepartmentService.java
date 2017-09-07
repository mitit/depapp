package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.domain.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    boolean persist(Department department);

    List<DepartmentDTO> getAll();
}
