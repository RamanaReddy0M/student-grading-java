package in.one2n.exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grader {

    /*
    * Using supplier to get stream two times,
    * 1 for setting capacity of arraylist
    * 2 for consuming students
    * */
    public List<Student> parseCSV(String filepath) {
        Supplier<Stream<String>> supplier = () -> {
            try {
                return Files.lines(Paths.get(filepath));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };

        List<Student> students = new ArrayList<Student>((int) supplier.get().count());
        supplier.get().skip(1).forEach((s -> {
            String[] data = s.split(",");
            Student student = new Student(data[0], data[1], data[2],
                    Double.parseDouble(data[3]),
                    Double.parseDouble(data[4]),
                    Double.parseDouble(data[5]),
                    Double.parseDouble(data[6]));
           students.add(student);
        }));
        return students;
    }

    /*
    * calcGrade() is a student class method it accepts no parameters but return grade of student, also
    * internally uses calcFinalScore() which return final score of student.
    * */
    public List<Student> calculateGrade(List<Student> students) {
        students.forEach(Student::calcGrade);
        return students;
    }

    public Student findOverallTopper(List<Student> gradedStudents) {
        Student topper = gradedStudents.stream()
                .max(Comparator.comparing(Student::getFinalScore))
                .get();
        return new Student(topper.getFirstname(), topper.getLastname(), topper.getUniversity());
    }


    public Map<String, Student> findTopperPerUniversity(List<Student> gradedStudents) {
        Map<String, Student> toppers = new HashMap<>();
        gradedStudents.stream()
                .collect(Collectors.groupingBy(Student::getUniversity))
                .forEach((k, v) -> {
                    toppers.put(k, findOverallTopper(v));
                });
        return toppers;
    }
}
