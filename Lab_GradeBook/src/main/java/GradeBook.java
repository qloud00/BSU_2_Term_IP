import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GradeBook {
    public Student student;
    public ArrayList<Session> sessions;
    
    public GradeBook(Student student_, ArrayList<Session> sessions_){
        this.student = student_;
        this.sessions = sessions_;
    }

    public Boolean isExcellentSession(Session session) {
        for(Session.Subject subject: session.subjects) {
            if(subject.exam < 9 || subject.exam > 10) return false;
        }
        return true;
    }

    public Double gpa(Session session){
        Double result = 0.0;
        Integer count = 0;
        for(Session.Subject subject: session.subjects){
            result += subject.exam;
            count++;
        }
        result/=count;
        return result;
    }

    public ArrayList<Integer> getExcellentSessionNumbers() {
        ArrayList<Integer> excellentSessions = new ArrayList<>();
        for (Integer i = 0; i < sessions.size(); i++) {
            if (isExcellentSession(sessions.get(i))) {
                excellentSessions.add(i + 1);
            }
        }
        return excellentSessions;
    }

    public Boolean hasExcellentSessions() {
        return !getExcellentSessionNumbers().isEmpty();
    }

    public void outFile(String filename, ArrayList<GradeBook> gradeBooks) throws IOException {
        try(FileWriter writer = new FileWriter(filename)){
            for(GradeBook gbBook: gradeBooks){
                if(gbBook.hasExcellentSessions()) {
                    Student student = gbBook.student;
                    ArrayList<Integer> excellentSessionNumbers = gbBook.getExcellentSessionNumbers();
                    
                    writer.write(String.format("%s %s %s, %d, %d, %s. \nОтличные сессии (номер): %s\n", 
                        student.surname, student.name, student.patronymic, 
                        student.course, student.group, student.speciality,
                        excellentSessionNumbers.toString()));
                    
                    for (Integer sessionIndex : excellentSessionNumbers) {
                        Session session = gbBook.sessions.get(sessionIndex - 1);
                        writer.write(String.format("\nСессия %d:\n", sessionIndex));
                        
                        for (Session.Subject subj : session.subjects) {
                            writer.write(String.format("%s: %d\n", subj.subject, subj.exam));
                        }
                        writer.write(String.format("GPA: %s\n", gpa(session)));
                    }
                    writer.write("\n\n");
                }
            }
        }
    }

    public static ArrayList<GradeBook> createFromFile(String filename, Integer subjectsPerStudent, Integer subjectsPerSession) throws FileNotFoundException {
        ArrayList<GradeBook> gradeBooks = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            
            if (line.isEmpty()) continue;
            
            if (line.contains(",")) {
                String[] studentData = line.split(",");
                if (studentData.length >= 4) {
                    String[] nameParts = studentData[0].trim().split(" ");
                    String surname = nameParts[0];
                    String name = nameParts[1];
                    String patronymic = nameParts[2];
                    
                    Integer course = Integer.parseInt(studentData[1].trim());
                    Integer group = Integer.parseInt(studentData[2].trim());
                    String speciality = studentData[3].trim();

                    Student student = new Student(
                        name, surname, patronymic, course, group, speciality
                    );

                    ArrayList<Session> sessions = new ArrayList<>();
                    
                    ArrayList<Session.Subject> allSubjects = new ArrayList<>();
                    for (Integer i = 0; i < subjectsPerStudent; i++) {
                        if (!scanner.hasNextLine()) break;
                        
                        String subjectLine = scanner.nextLine().trim();
                        if (subjectLine.isEmpty()) break;
                        
                        String[] subjectData = subjectLine.split(":");
                        String subjectName = subjectData[0].trim();
                        
                        String[] details = subjectData[1].trim().split(",");
                        String teacherFull = details[0].trim();
                        boolean isCredit = Boolean.parseBoolean(details[1].trim());
                        String examResultStr = details[2].trim();
                        
                        String[] teacherParts = teacherFull.split("\\s+");
                        String teacherSurname = teacherParts[0];
                        String teacherInitials = teacherParts.length > 1 ? teacherParts[1] : "";
                        String[] initials = teacherInitials.split("\\.");
                        String teacherName = initials.length > 0 ? initials[0] : "";
                        String teacherPatronymic = initials.length > 1 ? initials[1] : "";
                        
                        Integer examResult = null;
                        if (!examResultStr.equals("Retake")) {
                            examResult = Integer.parseInt(examResultStr);
                        } else {
                            examResult = 2;
                        }
                        
                        Session.Subject subject = 
                            new Session.Subject(
                                subjectName, 
                                teacherSurname, 
                                teacherName, 
                                teacherPatronymic,
                                isCredit, 
                                examResult
                            );
                        
                        allSubjects.add(subject);
                    }
                    
                    for (Integer i = 0; i < allSubjects.size(); i += subjectsPerSession) {
                        Session session = new Session();
                        Integer endIndex = Math.min(i + subjectsPerSession, allSubjects.size());
                        for (Integer j = i; j < endIndex; j++) {
                            session.subjects.add(allSubjects.get(j));
                        }
                        sessions.add(session);
                    }
                    
                    GradeBook gradeBook = new GradeBook(student, sessions);
                    gradeBooks.add(gradeBook);
                }
            }
        }
        scanner.close();
        
        return gradeBooks;
    }
    
    public static class Student{
        public String name;
        public String surname;
        public String patronymic;
        public String speciality;
        public Integer course;
        public Integer group;
        
        public Student(String surname_, String name_, String patronymic_, 
        Integer course_, Integer group_, String speciality_){
            this.surname = surname_;
            this.name = name_;
            this.patronymic = patronymic_;
            this.course = course_;
            this.group = group_;
            this.speciality = speciality_;
        }
    }
    
    public static class Session{
        public ArrayList<Subject> subjects = new ArrayList<>();
        
        public static class Subject {
            public String subject;
            public String teachersSurname;
            public String teachersName;
            public String teachersPatronymic;
            public Boolean credit;
            public Integer exam;

            public Subject(String subject_, String teachersSurname_, String teachersName_,
                        String teachersPatronymic_, Boolean credit_, Integer exam_) {
                this.subject = subject_;
                this.teachersSurname = teachersSurname_;
                this.teachersName = teachersName_;
                this.teachersPatronymic = teachersPatronymic_;
                this.credit = credit_;
                this.exam = exam_;
            }
        }
    }
}