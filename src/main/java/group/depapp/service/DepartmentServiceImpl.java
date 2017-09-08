package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.domain.DepartmentDTO;
import group.depapp.repository.DepartmentRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService{

    @Override
    public boolean save(List<DepartmentDTO> departmentDTOS) {
        DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();

        ArrayList<Department> departments = new ArrayList<>();

        for (DepartmentDTO departmentDTO : departmentDTOS) departments.add(departmentDTO.toDepartmentEntity());

        try {
            if (departments.size() == 1) {
                departmentRepository.save(departments.get(0));
            } else {
                departmentRepository.save(departments);
            }
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
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(List<DepartmentDTO> departmentDTOS) {
        DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();
        ArrayList<Department> departments = new ArrayList<>();

        for (DepartmentDTO departmentDTO : departmentDTOS) departments.add(departmentDTO.toDepartmentEntity());

        try {
            if (departments.size() == 1) {
                departmentRepository.delete(departments.get(0));
            } else {
                departmentRepository.delete(departments);
            }
                return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(DepartmentDTO departmentDTO) {
        DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();

        try {
                departmentRepository.update(departmentDTO.toDepartmentEntity());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
