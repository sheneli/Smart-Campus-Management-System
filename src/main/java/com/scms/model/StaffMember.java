package com.scms.model;

public class StaffMember extends User {
    private String department;
    private String position;

    public StaffMember(String userId, String name, String email, String password, String department, String position) {
        super(userId, name, email, password, UserRole.STAFF);
        this.department = department;
        this.position = position;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
