package com.scms.model;

public class Administrator extends User {
    private String department;

    public Administrator(String userId, String name, String email, String password, String department) {
        super(userId, name, email, password, UserRole.ADMINISTRATOR);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
