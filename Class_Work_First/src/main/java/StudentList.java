import java.io.*;
import java.util.*;

class Student {
    long num;
    String name;
    int group;
    double grade;

    public Student(long num, String name, int group, double grade) {
        this.num = num;
        this.name = name;
        this.group = group;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return num == student.num &&
                group == student.group &&
                Double.compare(student.grade, grade) == 0 &&
                Objects.equals(name, student.name);
    }

    @Override
    public String toString() {
        return num + " " + name + " " + group + " " + grade;
    }
}

public class StudentList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Введите номер операции: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {;
                break;
            }

            try {
                String file1 = getInput(scanner, "Введите имя первого файла: ");
                String file2 = getInput(scanner, "Введите имя второго файла: ");
                String outputFile = getInput(scanner, "Введите имя выходного файла: ");

                List<Student> list1 = readStudentsFromFile(file1);
                List<Student> list2 = readStudentsFromFile(file2);
                List<Student> result = new ArrayList<>();

                switch (choice) {
                    case 1:
                        result = union(list1, list2);
                        break;
                    case 2:
                        result = intersection(list1, list2);
                        break;
                    case 3:
                        result = difference(list1, list2);
                        break;
                }

                writeStudentsToFile(outputFile, result);
                System.out.println("Результат записан в файл");

            } catch (IOException e) {
                System.err.println("Ошибка при работе с файлами: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("Операции:");
        System.out.println("1. Объединение");
        System.out.println("2. Пересечение");
        System.out.println("3. Разность");
        System.out.println("0. Выход");
        System.out.println();
    }

    private static String getInput(Scanner scanner, String text) {
        System.out.print(text);
        return scanner.nextLine().trim();
    }

    private static List<Student> readStudentsFromFile(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Student student = parseStudent(line);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        }
        return students;
    }

    private static Student parseStudent(String line) {
        try {
            String[] parts = line.trim().split("\\s+", 4);
            if (parts.length >= 4) {
                long num = Long.parseLong(parts[0]);
                String name = parts[1];
                int group = Integer.parseInt(parts[2]);
                double grade = Double.parseDouble(parts[3]);
                return new Student(num, name, group, grade);
            }
        } catch (NumberFormatException e) {
            System.err.println("Ошибка " + e.getMessage());
        }
        return null;
    }

    private static void writeStudentsToFile(String filename, List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        }
    }

    private static List<Student> union(List<Student> list1, List<Student> list2) {
        List<Student> result = new ArrayList<>();

        // Добавляем все элементы из первого списка
        for (Student student : list1) {
            if (!containsStudent(result, student)) {
                result.add(student);
            }
        }

        // Добавляем элементы из второго списка, которых нет в результате
        for (Student student : list2) {
            if (!containsStudent(result, student)) {
                result.add(student);
            }
        }

        return result;
    }

    private static List<Student> intersection(List<Student> list1, List<Student> list2) {
        List<Student> result = new ArrayList<>();

        for (Student student : list1) {
            if (containsStudent(list2, student) && !containsStudent(result, student)) {
                result.add(student);
            }
        }

        return result;
    }

    private static List<Student> difference(List<Student> list1, List<Student> list2) {
        List<Student> result = new ArrayList<>();

        for (Student student : list1) {
            if (!containsStudent(list2, student) && !containsStudent(result, student)) {
                result.add(student);
            }
        }

        return result;
    }

    private static boolean containsStudent(List<Student> list, Student student) {
        for (Student s : list) {
            if (s.equals(student)) {
                return true;
            }
        }
        return false;
    }
}