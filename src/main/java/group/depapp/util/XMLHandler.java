package group.depapp.util;

import group.depapp.domain.DepartmentDTO;
import group.depapp.service.DepartmentService;
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
public class XMLHandler {

    private DepartmentService departmentService;

    @Autowired
    public XMLHandler(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public void create() {

        List<DepartmentDTO> departmentDTOList = departmentService.getAll();

        for (DepartmentDTO departmentDTO : departmentDTOList)
            System.out.println("c:" + departmentDTO.getDepCode() + " j:" +
                    departmentDTO.getDepJob() + " desc:" + departmentDTO.getDescription());

        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("table");
            doc.appendChild(rootElement);

            for (DepartmentDTO departmentDTO : departmentDTOList) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DepartmentDTO> parse() {
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        try {
            File inputFile = new File("db.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("department");
            System.out.println("----------------------------");


            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                DepartmentDTO departmentDTO = new DepartmentDTO();

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    departmentDTO.setDepCode(eElement.getElementsByTagName("depCode").item(0).getTextContent());
                    departmentDTO.setDepJob(eElement.getElementsByTagName("depJob").item(0).getTextContent());

                    if (eElement.getElementsByTagName("description").item(0) != null) {
                        departmentDTO.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
                    }
                }

                departmentDTOList.add(departmentDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (DepartmentDTO departmentDTO : departmentDTOList)
            System.out.println("c:" + departmentDTO.getDepCode() + " j:" +
                    departmentDTO.getDepJob() + " desc:" + departmentDTO.getDescription());

        return departmentDTOList;
    }
}
