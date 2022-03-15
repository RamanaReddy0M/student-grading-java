package in.one2n.exercise;

public class Student {

    private String firstname;
    private String lastname;
    private String university;
    private Double test1Score;
    private Double test2Score;
    private Double test3Score;
    private Double test4Score;

    // computed fields
    private Double finalScore;
    private Grade grade;

    public Student(String firstname, String lastname, String university) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.university = university;
    }

    public Student(String firstname, String lastname, String university, Double test1Score, Double test2Score, Double test3Score, Double test4Score) {
       this.firstname = firstname;
       this.lastname = lastname;
       this.university = university;
       this.test1Score = test1Score;
       this.test2Score =  test2Score;
       this.test3Score = test3Score;
       this.test4Score = test4Score;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUniversity() {
        return university;
    }

    public Double getTest1Score() {
        return test1Score;
    }

    public Double getTest2Score() {
        return test2Score;
    }

    public Double getTest3Score() {
        return test3Score;
    }

    public Double getTest4Score() {
        return test4Score;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public Grade getGrade() {
        return grade;
    }

    public Grade calcGrade(){
        calcFinalScore();
        if(finalScore < 35)
            grade = Grade.F;
        else if(finalScore >= 35 && finalScore < 50)
            grade = Grade.C;
        else if(finalScore >= 50 && finalScore < 70)
            grade = Grade.B;
        else
            grade = Grade.A;
        return grade;
    }

    public Double calcFinalScore(){
        finalScore = (test1Score+test2Score+test3Score+test4Score) / 4;
        return finalScore;
    }

    public static Student deepCopy(Student s){
        Student student = new Student(s.getFirstname(),
                s.getLastname(),
                s.getUniversity(),
                s.getTest1Score(),
                s.getTest2Score(),
                s.getTest3Score(),
                s.getTest4Score());
        student.calcGrade();
        return student;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        if(!this.toString().equals(obj.toString()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", university='" + university + '\'' +
                ", test1Score=" + test1Score +
                ", test2Score=" + test2Score +
                ", test3Score=" + test3Score +
                ", test4Score=" + test4Score +
                ", finalScore=" + finalScore +
                ", grade=" + grade +
                '}';
    }
}

