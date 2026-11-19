package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class WriterDom {

    public static void writeStudentsToXml(ArrayList<Student> students, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("students");
            doc.appendChild(rootElement);

            for (Student student : students) {
                Element studentElem = doc.createElement("student");
                studentElem.setAttribute("id", String.valueOf(student.getId()));

                Element nameElem = doc.createElement("name");
                nameElem.appendChild(doc.createTextNode(student.getName()));
                studentElem.appendChild(nameElem);

                Element ageElem = doc.createElement("age");
                ageElem.appendChild(doc.createTextNode(String.valueOf(student.getAge())));
                studentElem.appendChild(ageElem);

                Element groupElem = doc.createElement("group");
                groupElem.appendChild(doc.createTextNode(student.getGroup()));
                studentElem.appendChild(groupElem);

                rootElement.appendChild(studentElem);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("DOM: Файл " + filePath + " успешно создан!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
