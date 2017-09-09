package group.depapp.service;

import group.depapp.domain.Department;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

@Component
public class XMLServiceImpl implements XMLService {

    private static final Logger log = Logger.getLogger(XMLServiceImpl.class);


    private final DepartmentService departmentService;

    @Autowired
    public XMLServiceImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public void create() {

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

                if (departmentDTO.getDescription() != null) {
                    Element desc = doc.createElement("description");
                    desc.appendChild(doc.createTextNode(departmentDTO.getDescription()));
                    department.appendChild(desc);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("db.xml"));
            transformer.transform(source, result);
            log.info("XML FILE CREATED");
        } catch (Exception e) {
            log.error("ERROR CREATING XML FILE" + e.getMessage(), e);
        }
    }

    @Override
    public List<Department> parse() {
        final List<Department> departmentList = new ArrayList<>();
        try {
            File inputFile = new File("db.xml");
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

                    if (eElement.getElementsByTagName("description").item(0) != null) {
                        department.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                    }
                }
                departmentList.add(department);
                log.info("XML FILE PARSED");
            }
        } catch (Exception e) {
            log.error("ERROR PARSING XML FILE" + e.getMessage(), e);
        }

        return departmentList;
    }
}
