package in.one2n.exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Grader {

    public static void main(String[] args) {
        Grader grader = new Grader();
        List<Student> students = grader.calculateGrade(
                grader.parseCSV(Paths.get("src", "test", "resources", "grades.csv").toString()));
        grader.findTopperPerUniversity(students);

    }

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
        if(gradedStudents.size() < 1)
            return null;
        double max = 0.0d;
        Student topper = null;
        for(Student s: gradedStudents)
            if(s.getFinalScore() > max){
                max = s.getFinalScore();
                topper = s;
            }
        return new Student(topper.getFirstname(), topper.getLastname(), topper.getUniversity());
    }

    /*
    * uses comparator to sort list by university
    * here, findOverAllTopper() return new copy of student
    * */
    public Map<String, Student> findTopperPerUniversity(List<Student> gradedStudents) {

        Map<String, Student> toppers = new HashMap<>();
        List<Student> students = new ArrayList<>(gradedStudents);
        //for processing last record
        students.add(new Student("", "", "{}"));
        Comparator<Student> compareByUniversity = (s1, s2) -> s1.getUniversity().compareTo(s2.getUniversity());
        Collections.sort(students, compareByUniversity);
        //for storing students by university
        List<Student> temp = new ArrayList<>();
        for(int i=0; i < students.size()-1; i++) {
            if (students.get(i).getUniversity()
                    .equalsIgnoreCase(students.get(i + 1).getUniversity())) {
                temp.add(students.get(i));
            } else {
                temp.add(students.get(i));
                Student s = findOverallTopper(temp);
                toppers.put(s.getUniversity(), s);
                temp.clear();
            }
        }
        return new HashMap<>(toppers);
    }
}
