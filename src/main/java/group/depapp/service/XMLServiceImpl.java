package group.depapp.service;

import group.depapp.domain.Department;
import group.depapp.exception.FieldTooLongException;
import group.depapp.exception.FileContainsSameObjectsException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLServiceImpl implements XMLService {

    private static final Logger log = Logger.getLogger(XMLServiceImpl.class);

    private final DepartmentService departmentService;

    @Autowired
    public XMLServiceImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public void create(String pathname) {

        final List<Department> departmentList = departmentService.getAll();

        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("table");
            doc.appendChild(rootElement);

            for (Department departmentDTO : departmentList) {
                Element department = doc.createElement("department");
                rootElement.appendChild(department);

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

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathname));
            transformer.transform(source, result);
            log.info("XML FILE CREATED");
        } catch (Exception e) {
            log.error("ERROR CREATING XML FILE" + e.getMessage(), e);
        }
    }

    @Override
    public List<Department> loadData(String pathname) throws FileContainsSameObjectsException, FieldTooLongException {
        final List<Department> departmentList = new ArrayList<>();
        try {
            File inputFile = new File(pathname);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("department");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                Department department = new Department();

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    department.setDepCode(eElement.getElementsByTagName("depCode").item(0).getTextContent());
                    department.setDepJob(eElement.getElementsByTagName("depJob").item(0).getTextContent());
                    department.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                }
                departmentList.add(department);
                log.info("XML FILE PARSED");
            }
        } catch (Exception e) {
            log.error("ERROR PARSING XML FILE" + e.getMessage(), e);
        }

        for (int i = 0; i < departmentList.size(); i++) {
            for (int j = 0; j < departmentList.size(); j++) {
                if (departmentList.get(i) == departmentList.get(j) && i != j)
                    throw new FileContainsSameObjectsException();
            }

            if (departmentList.get(i).getDescription().length() > 255
                    || departmentList.get(i).getDepJob().length() > 100
                    || departmentList.get(i).getDepCode().length() > 20) throw new FieldTooLongException();
        }

        return departmentList;
    }
}
