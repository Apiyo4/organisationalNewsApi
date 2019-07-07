package models;

public class Department {
    private int id;
    private String departmentName;
    private String description;
    private int numberOfEmoployees;

    public Department(String departmentName, String description, int numberOfEmployees) {
        this.departmentName = departmentName;
        this.description = description;
        this.numberOfEmoployees = numberOfEmoployees;
    }
}
