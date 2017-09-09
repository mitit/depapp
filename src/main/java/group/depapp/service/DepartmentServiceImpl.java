package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.repository.DepartmentRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger log = Logger.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository<Department> departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository<Department> departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public boolean save(List<Department> departments) {
        try {
            if (departments.size() == 1) {
                departmentRepository.save(departments.get(0));
            } else {
                departmentRepository.save(departments);
            }
            log.info("DATA SAVED");
            return true;
        } catch (Exception e) {
            log.error("ERROR SAVING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Department> getAll() {
        return departmentRepository.getAll();
//                .stream()
//                .map(Department::new)
//                .collect(Collectors.toList())
    }

    @Override
    public boolean delete(List<Department> departments) {
        try {
            if (departments.size() == 1) {
                departmentRepository.delete(departments.get(0));
            } else {
                departmentRepository.delete(departments);
            }
            log.info("DATA DELETED");
            return true;
        } catch (Exception e) {
            log.error("ERROR DELETING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean update(Department department) {
        try {
            departmentRepository.update(department.toDepartmentEntity());
            log.info("DATA UPDATED");
            return true;
        } catch (Exception e) {
            log.error("ERROR UPDATING DATA: " + e.getMessage(), e);
            return false;
        }
    }
}
