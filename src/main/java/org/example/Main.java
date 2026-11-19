package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String fileName = "students.xml";
        ArrayList<Student> originalList = new ArrayList<>();
        originalList.add(new Student(1, "Иванов Иван", 20, "бИЦ-241"));
        originalList.add(new Student(2, "Петров Петр", 21, "бТИИ-242"));
        originalList.add(new Student(3, "Александров Александр", 19, "бГСХ-241"));

        System.out.println("Исходный список");
        originalList.forEach(System.out::println);
        WriterDom.writeStudentsToXml(originalList, fileName);
        ArrayList<Student> parsedList = ReaderSax.readStudentsFromXml(fileName);
        System.out.println("Список SAX");
        parsedList.forEach(System.out::println);
    }
}