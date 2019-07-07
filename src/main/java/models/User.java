package models;

public class User {
    private int id;
    private String userName;
    private String departmentsId;
    private String role;

    public User(String userName, String departmentsId, String role) {
        this.userName = userName;
        this.departmentsId = departmentsId;
        this.role = role;
    }
}
