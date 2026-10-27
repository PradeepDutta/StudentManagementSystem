
package model;

public class Student {
    private int id;
    private String name;
    private String course;
    private String email;

    public Student(int id, String name, String course, String email) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.email = email;
    }

    public Student(String name, String course, String email) {
        this(0, name, course, email);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getEmail() {
        return email;
    }
}
