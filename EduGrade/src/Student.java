import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Subject> subjects;

    public Student(String name) {
        this.name = name;
        this.subjects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(String subjectName, double grade) {
        subjects.add(new Subject(subjectName, grade));
    }

    public void removeSubject(int index) {
        if (index >= 0 && index < subjects.size()) {
            subjects.remove(index);
        }
    }

    public double calculateGWA() {
        if (subjects.isEmpty()) return 0.0;
        double total = 0;
        for (Subject subject : subjects) {
            total += subject.getGrade();
        }
        return total / subjects.size();
    }
}
