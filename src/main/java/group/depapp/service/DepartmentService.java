package group.depapp.service;

import group.depapp.domain.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    boolean save(List<DepartmentDTO> departmentDTOS);

    List<DepartmentDTO> getAll();

    boolean delete(List<DepartmentDTO> departmentDTOS);

    boolean update(DepartmentDTO departmentDTO);
}
