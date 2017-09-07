package group.depapp.util;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import group.depapp.domain.Department;
import group.depapp.domain.DepartmentDTO;
import group.depapp.service.DepartmentService;
import group.depapp.service.DepartmentServiceImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;


public class CreateXMLFile {

    public void create() {

        DepartmentServiceImpl departmentService = new DepartmentServiceImpl();
        List<DepartmentDTO> departmentDTOList = departmentService.getAll();

        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("table");
            doc.appendChild(rootElement);

            for (DepartmentDTO departmentDTO : departmentDTOList) {
                Element department = doc.createElement("department");
                Attr attrID = doc.createAttribute("id");
                attrID.setValue(departmentDTO.getId().toString());

                Element depCode = doc.createElement("depCode");
                depCode.appendChild(doc.createTextNode(departmentDTO.getDepCode()));
                department.appendChild(depCode);

                Element depJob = doc.createElement("depJob");
                depJob.appendChild(doc.createTextNode(departmentDTO.getDepJob()));
                department.appendChild(depJob);

                Element desc = doc.createElement("description");
                desc.appendChild(doc.createTextNode(departmentDTO.getDescription()));
                department.appendChild(desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
