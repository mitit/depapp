package group.depapp.service;

import group.depapp.domain.Department;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SynchronizeServiceImpl implements SynchronizeService {

    private static final Logger log = Logger.getLogger(SynchronizeServiceImpl.class);


    private DepartmentService departmentService;

    @Autowired
    public SynchronizeServiceImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    @Transactional
    public void synchronize() {
        XMLService xmlService = new XMLServiceImpl(departmentService);

        List<Department> departmentsFromXML = xmlService.parse();
        List<Department> departmentsFromDB = departmentService.getAll();

        List<Department> departmentsToDelete = new ArrayList<>();
        List<Department> departmentsToInsert = new ArrayList<>();

        Map<Department, Boolean> mapForSynchronization = new HashMap<>();
        departmentsFromXML.forEach(department -> mapForSynchronization.put(department, false));

        departmentsFromDB.forEach(department -> {
            if (!mapForSynchronization.containsKey(department)) {
                departmentsToDelete.add(department);
            } else {
                mapForSynchronization.entrySet().stream()
                        .filter(entry -> entry.getKey().equals(department) && entry.getKey().hashCode() == department.hashCode())
                        .forEach(entry -> {
                            if (!entry.getKey().getDescription().equals(department.getDescription())) {
                                departmentService.update(entry.getKey());
                            }
                            entry.setValue(true);
                        });
            }
        });


        for (Map.Entry<Department, Boolean> entry : mapForSynchronization.entrySet()) {
            if (!entry.getValue()) departmentsToInsert.add(entry.getKey());
        }
        if (departmentsToInsert.size() > 0)
            departmentService.save(departmentsToInsert);

        if (true) throw new RuntimeException();

        if (departmentsToDelete.size() > 0)
            departmentService.delete(departmentsToDelete);

        log.info("DATA SYNCHRONIZED");
    }
}
