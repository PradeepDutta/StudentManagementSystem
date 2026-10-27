
import dao.StudentDAO;
import model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("   STUDENT MANAGEMENT SYSTEM");
        System.out.println("==================================");

        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> viewStudents();
                    case "3" -> updateStudent();
                    case "4" -> deleteStudent();
                    case "5" -> {
                        running = false;
                        System.out.println("Thank you for using the system!");
                    }
                    default -> System.out.println("Invalid choice. Enter 1-5.");
                }
            } catch (SQLException e) {
                System.out.println("Database operation failed: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addStudent() throws SQLException {
        System.out.println("\n--- Add Student ---");

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter course: ");
        String course = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        if (name.isEmpty() || course.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        Student student = new Student(name, course, email);

        if (studentDAO.addStudent(student)) {
            System.out.println("Student added successfully!");
        }
    }

    private static void viewStudents() throws SQLException {
        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.printf(
            "%-5s | %-22s | %-22s | %-30s%n",
            "ID", "NAME", "COURSE", "EMAIL"
        );
        System.out.println("-".repeat(88));

        for (Student student : students) {
            System.out.printf(
                "%-5d | %-22s | %-22s | %-30s%n",
                student.getId(),
                student.getName(),
                student.getCourse(),
                student.getEmail()
            );
        }
    }

    private static void updateStudent() throws SQLException {
        System.out.print("Enter student ID to update: ");
        int id = readId();

        if (id <= 0) {
            System.out.println("Please enter a valid positive ID.");
            return;
        }

        System.out.print("Enter new name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter new course: ");
        String course = scanner.nextLine().trim();

        System.out.print("Enter new email: ");
        String email = scanner.nextLine().trim();

        if (name.isEmpty() || course.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        Student student = new Student(id, name, course, email);

        if (studentDAO.updateStudent(student)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("No student found with that ID.");
        }
    }

    private static void deleteStudent() throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = readId();

        if (id <= 0) {
            System.out.println("Please enter a valid positive ID.");
            return;
        }

        System.out.print("Delete student ID " + id + "? Type YES to confirm: ");

        if (scanner.nextLine().trim().equals("YES")) {
            if (studentDAO.deleteStudent(id)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with that ID.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static int readId() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
