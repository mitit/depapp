package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.domain.DepartmentDTO;
import group.depapp.repository.DepartmentRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService{

    @Override
    public boolean persist(Department department) {
        DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();

        try {
            departmentRepository.persist(department);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<DepartmentDTO> getAll() {
        DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();


        return departmentRepository.getAll()
                .stream()
                .map(DepartmentDTO::new)
                .sorted()
                .collect(Collectors.toList());
    }
}
