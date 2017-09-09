package group.depapp.util;

import group.depapp.domain.DepartmentDTO;
import group.depapp.service.DepartmentService;
import group.depapp.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Synchronizer {

    DepartmentService departmentService;

    @Autowired
    public Synchronizer(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Transactional
    public void synchronize() {
        XMLHandler xmlHandler = new XMLHandler(departmentService);


        List<DepartmentDTO> dtosFromXML = xmlHandler.parse();
        List<DepartmentDTO> dtosFromDB = departmentService.getAll();

        Map<DepartmentDTO, Boolean> mapForSynchronization = new HashMap<>();

        List<DepartmentDTO> departmentsToDelete = new ArrayList<>();
        List<DepartmentDTO> departmentsToInsert = new ArrayList<>();

        for (DepartmentDTO departmentDTO : dtosFromXML) mapForSynchronization.put(departmentDTO, false);

        for (DepartmentDTO departmentDTO : dtosFromDB) {

            if (!mapForSynchronization.containsKey(departmentDTO)) {
                departmentsToDelete.add(departmentDTO);
            } else {


                for (Map.Entry<DepartmentDTO, Boolean> entry : mapForSynchronization.entrySet()) {

                    if (entry.getKey().equals(departmentDTO) && entry.getKey().hashCode() == departmentDTO.hashCode()) {
                        if (!entry.getKey().getDescription().equals(departmentDTO.getDescription())) {
                            departmentService.update(entry.getKey());
                        }
                        entry.setValue(true);

                    }
                }
            }
        }

        for (Map.Entry<DepartmentDTO, Boolean> entry : mapForSynchronization.entrySet()) {
            if (!entry.getValue()) departmentsToInsert.add(entry.getKey());
        }
        if (departmentsToInsert.size() > 0)
            departmentService.save(departmentsToInsert);
        if (departmentsToDelete.size() > 0)
            departmentService.delete(departmentsToDelete);
    }
}
