//package group.depapp.util;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import group.depapp.domain.Department;
//import group.depapp.repository.DepartmentRepositoryImpl;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import java.sql.ResultSet;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//public class XMLMaker {
//    Document mapDoc;
//    Document dataDoc;
//    Document newDoc;
//    Element mapRoot;
//
//    public void findXML() {
//        try {
//            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
//            mapDoc = docbuilder.parse("dep.xml");
//            dataDoc = docbuilder.newDocument();
//            newDoc = docbuilder.newDocument();
//        } catch (Exception e) {
//            System.out.println("Problem creating document: " + e.getMessage());
//        }
//        mapRoot = mapDoc.getDocumentElement();
//
//        Node dataNode = mapRoot.getElementsByTagName("data").item(0);
//
//        Element dataElement = (Element)dataNode;
//        String sql = dataElement.getAttribute("sql");
//
//        System.out.println(sql);
//
//    }
//
//    public void makeXML() {
//        DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();
//        SqlRowSet rs = departmentRepository.getAll();
//
//        Set<Department> departmentSet = new HashSet<>();
//        while (rs.next()) {
//            Department department = new Department();
//            department.setId(rs.getInt(1));
//            department.setDepCode(rs.getString(2));
//            department.setDepJob(rs.getString(3));
//            department.setDescription(rs.getString(4));
//            departmentSet.add(department);
//        }
//
//        SqlRowSetMetaData metaData = rs.getMetaData();
//        Element dataRoot = dataDoc.createElement("data");
//        int numCols = metaData.getColumnCount();
//
//
//        while (rs.next()) {
//            //Для каждой строки данных
//            // Создание нового элемента с именем "row"
//            Element rowEl = dataDoc.createElement("row");
//            for (int i=1; i <= numCols; i++) {
//                // Для каждого столбца, выборка имени и данных
//                String colName = metaData.getColumnName(i);
//                String colVal = rs.getString(i);
//                //Если нет данных, добавить "and up"
//                if (rs.wasNull()) {
//                    colVal = "and up";
//                }
//                // Создание нового элемента с тем же именем, что и у столбца
//                Element dataEl = dataDoc.createElement(colName);
//                //Добавление данных к новому элементу
//                dataEl.appendChild(dataDoc.createTextNode(colVal));
//                // Добавление нового элемента к строке
//                rowEl.appendChild(dataEl);
//            }
//            //Добавление строки к корневому элементу
//            dataRoot.appendChild(rowEl);
//        }
//        dataDoc.appendChild(dataRoot);
//
//        //Выборка корневого элемента (называвемого "root")
//        Element newRootInfo = (Element)mapRoot.getElementsByTagName("root").item(0);
//        // Выборка информации о корне и строке
//        String newRootName = newRootInfo.getAttribute("name");
//        String newRowName = newRootInfo.getAttribute("rowName");
//        // Выборка информации об элементе, встраиваемом в новый документ
//        NodeList newNodesMap = mapRoot.getElementsByTagName("element");
//        //Создание конечного корневого элемента с именем из файла отображения
//        Element newRootElement = newDoc.createElement(newRootName);
//
//        //Выборка всех строк в старом документе
//        NodeList oldRows = dataRoot.getElementsByTagName("row");
//        for (int i=0; i < oldRows.getLength(); i++){
//            //Для каждой из исходных строк
//            Element thisRow = (Element)oldRows.item(i);
//            //Создание новой строки
//            Element newRow = newDoc.createElement(newRowName);
//            for (int j=0; j < newNodesMap.getLength(); j++) {
//                //Получение информации отображения для каждого столбца
//                Element thisElement = (Element)newNodesMap.item(j);
//                String newElementName = thisElement.getAttribute("name");
//                Element oldElement = (Element)thisElement.getElementsByTagName("content").item(0);
//                String oldField = oldElement.getFirstChild().getNodeValue();
//                //Получение исходных значений на основе информации отображения
//                Element oldValueElement = (Element)thisRow.getElementsByTagName(oldField).item(0);
//                String oldValue = oldValueElement.getFirstChild().getNodeValue();
//                //Создание нового элемента
//                Element newElement = newDoc.createElement(newElementName);
//                newElement.appendChild(newDoc.createTextNode(oldValue));
//
//                NodeList newAttributes = thisElement.getElementsByTagName("attribute");
//                for (int k=0; k < newAttributes.getLength(); k++) {
//                    //Для каждого нового атрибута
//                    //Получение информации отображения
//                    Element thisAttribute = (Element)newAttributes.item(k);
//                    String oldAttributeField = thisAttribute.getFirstChild().getNodeValue();
//                    String newAttributeName = thisAttribute.getAttribute("name");
//                    //Получение исходного значения
//                    oldValueElement = (Element)thisRow.getElementsByTagName(oldAttributeField).item(0);
//                    String oldAttributeValue = oldValueElement.getFirstChild().getNodeValue();
//                    //Создание нового атрибута
//                    newElement.setAttribute(newAttributeName, oldAttributeValue);
//                }
//
//                //Добавление нового элемента к новой строке
//                newRow.appendChild(newElement);
//            }
//            //Добавление новой строки к корню
//            newRootElement.appendChild(newRow);
//        }
//        //Добавление нового корня к документу
//        newDoc.appendChild(newRootElement);
//        System.out.println("PRIVET");
//    }
//}
