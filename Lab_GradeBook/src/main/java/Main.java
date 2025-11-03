import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    
    public void run() {
        try {
            ArrayList<GradeBook> gradeBooks = GradeBook.createFromFile("input.txt", 16, 2);
            if (!gradeBooks.isEmpty()) {
                GradeBook firstGradeBook = gradeBooks.get(0);
                firstGradeBook.outFile("output.txt", gradeBooks);
            }
        } 
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}