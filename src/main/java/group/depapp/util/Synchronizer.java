package group.depapp.util;

import group.depapp.domain.Department;
import group.depapp.service.DepartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Synchronizer {

    private static final Logger log = Logger.getLogger(Synchronizer.class);


    private DepartmentService departmentService;

    @Autowired
    public Synchronizer(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Transactional
    public void synchronize() {
        XMLHandler xmlHandler = new XMLHandler(departmentService);


        List<Department> dtosFromXML = xmlHandler.parse();
        List<Department> dtosFromDB = departmentService.getAll();

        Map<Department, Boolean> mapForSynchronization = new HashMap<>();

        List<Department> departmentsToDelete = new ArrayList<>();
        List<Department> departmentsToInsert = new ArrayList<>();

        dtosFromXML.forEach(dto -> mapForSynchronization.put(dto, false));
        dtosFromDB.forEach(dto -> {
            if (!mapForSynchronization.containsKey(dto)) {
                departmentsToDelete.add(dto);
            } else {
                mapForSynchronization.entrySet().stream()
                        .filter(entry -> entry.getKey().equals(dto) && entry.getKey().hashCode() == dto.hashCode())
                        .forEach(entry -> {
                    if (!entry.getKey().getDescription().equals(dto.getDescription())) {
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
        if (departmentsToDelete.size() > 0)
            departmentService.delete(departmentsToDelete);
    }
}
