package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.domain.DepartmentDTO;
import group.depapp.repository.DepartmentRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger log = Logger.getLogger(DepartmentServiceImpl.class);

    DepartmentRepository<Department> departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository<Department> departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public boolean save(List<DepartmentDTO> departmentDTOS) {

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
            log.error("ERROR SAVING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<DepartmentDTO> getAll() {

        return departmentRepository.getAll()
                .stream()
                .map(DepartmentDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(List<DepartmentDTO> departmentDTOS) {
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
            log.error("ERROR DELETING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean update(DepartmentDTO departmentDTO) {

        try {
                departmentRepository.update(departmentDTO.toDepartmentEntity());
            return true;
        } catch (Exception e) {
            log.error("ERROR UPDATING DATA: " + e.getMessage(), e);
            return false;
        }
    }
}
