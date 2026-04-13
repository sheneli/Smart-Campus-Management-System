package com.scms.model;

public enum UserRole {
    ADMINISTRATOR("Administrator"),
    STAFF("Staff Member"),
    STUDENT("Student");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
