package com.scms.service;

import com.scms.exception.DuplicateDataException;
import com.scms.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    private final Map<String, User> users = new LinkedHashMap<>();

    public UserService() {
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        addUser(new Administrator("ADM-001", "Dr. Sarah Wilson", "admin@cardiffmet.ac.uk", "admin123", "IT Department"));
        addUser(new Administrator("ADM-002", "Prof. James Clark", "james.clark@cardiffmet.ac.uk", "admin456", "Facilities"));
        addUser(new StaffMember("STF-001", "Dr. Emily Brown", "emily.brown@cardiffmet.ac.uk", "staff123", "Computer Science", "Senior Lecturer"));
        addUser(new StaffMember("STF-002", "Mr. David Jones", "david.jones@cardiffmet.ac.uk", "staff456", "Engineering", "Lecturer"));
        addUser(new StaffMember("STF-003", "Dr. Lisa Taylor", "lisa.taylor@cardiffmet.ac.uk", "staff789", "Mathematics", "Associate Professor"));
        addUser(new Student("STU-001", "Alex Thompson", "alex.thompson@student.cardiffmet.ac.uk", "student123", "MSc IT", 1));
        addUser(new Student("STU-002", "Maria Garcia", "maria.garcia@student.cardiffmet.ac.uk", "student456", "BSc Computer Science", 2));
        addUser(new Student("STU-003", "John Smith", "john.smith@student.cardiffmet.ac.uk", "student789", "MSc Data Science", 1));
        addUser(new Student("STU-004", "Emma Davis", "emma.davis@student.cardiffmet.ac.uk", "student101", "BSc Software Engineering", 3));
    }

    // Defines the `addUser` method.
    public void addUser(User user) {
        if (users.containsKey(user.getUserId())) {
            throw new DuplicateDataException("User already exists: " + user.getUserId());
        }
        users.put(user.getUserId(), user);
    }

    public User authenticate(String email, String password) {
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.authenticate(password))
                .findFirst()
                .orElse(null);
    }

    public User findById(String userId) {
        return users.get(userId);
    }

    public User findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<User> getUsersByRole(UserRole role) {
        return users.values().stream()
                .filter(u -> u.getRole() == role)
                .collect(Collectors.toList());
    }

    public int getUserCount() {
        return users.size();
    }
}
