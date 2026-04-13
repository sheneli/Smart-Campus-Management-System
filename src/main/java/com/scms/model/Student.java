package com.scms.model;

public class Student extends User {
    private String programme;
    private int year;

    public Student(String userId, String name, String email, String password, String programme, int year) {
        super(userId, name, email, password, UserRole.STUDENT);
        this.programme = programme;
        this.year = year;
    }

    public String getProgramme() { return programme; }
    public void setProgramme(String programme) { this.programme = programme; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
