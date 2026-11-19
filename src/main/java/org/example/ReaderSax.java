package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;

public class ReaderSax {
    private static class StudentHandler extends DefaultHandler {
        private ArrayList<Student> students = new ArrayList<>();
        private Student currentStudent;
        private StringBuilder currentValue = new StringBuilder();

        public ArrayList<Student> getStudents() {
            return students;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if ("student".equals(qName)) {
                currentStudent = new Student();
                // Читаем атрибут id
                currentStudent.setId(Integer.parseInt(attributes.getValue("id")));
            }
            currentValue.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            currentValue.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (currentStudent == null) return;
            String value = currentValue.toString().trim();
            switch (qName) {
                case "name":
                    currentStudent.setName(value);
                    break;
                case "age":
                    currentStudent.setAge(Integer.parseInt(value));
                    break;
                case "group":
                    currentStudent.setGroup(value);
                    break;
                case "student":
                    students.add(currentStudent);
                    currentStudent = null;
                    break;
            }
        }
    }

    public static ArrayList<Student> readStudentsFromXml(String filePath) {
        ArrayList<Student> students = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            StudentHandler handler = new StudentHandler();
            saxParser.parse(new File(filePath), handler);

            students = handler.getStudents();
            System.out.println("SAX: Файл " + filePath + " успешно прочитан!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}