package group.depapp.util;

import group.depapp.domain.DepartmentDTO;
import group.depapp.service.DepartmentServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Synchronizer {

    public void synchronize(){
        XMLHandler xmlHandler = new XMLHandler();
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl();

        List<DepartmentDTO> dtosFromXML = xmlHandler.parse();
        List<DepartmentDTO> dtosFromDB = departmentService.getAll();

        Map<DepartmentDTO, Boolean> mapForSynchronization = new HashMap<>();
        Boolean isInDB = false;

        for (DepartmentDTO departmentDTO : dtosFromXML) mapForSynchronization.put(departmentDTO, isInDB);

        for (DepartmentDTO departmentDTO : dtosFromDB) {

            if (!mapForSynchronization.containsKey(departmentDTO)) {

            }




        }
    }
}
