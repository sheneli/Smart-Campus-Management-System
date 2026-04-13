package com.scms.service;

import com.scms.model.MaintenanceRequest;

import java.util.*;
import java.util.stream.Collectors;

public class MaintenanceService {

    private final Map<String, MaintenanceRequest> requests = new LinkedHashMap<>();
    private int requestCounter = 0;

    public MaintenanceService() {
        initializeDefaultRequests();
    }

    private void initializeDefaultRequests() {
        MaintenanceRequest r1 = new MaintenanceRequest("MR-001", "RM-001", "STF-001",
                "Projector flickering during presentations", MaintenanceRequest.Urgency.HIGH);
        requests.put(r1.getRequestId(), r1);

        MaintenanceRequest r2 = new MaintenanceRequest("MR-002", "RM-003", "STU-002",
                "Three computers not turning on", MaintenanceRequest.Urgency.MEDIUM);
        r2.setAssignedTo("Facilities Team");
        r2.setStatus(MaintenanceRequest.Status.ASSIGNED);
        requests.put(r2.getRequestId(), r2);

        MaintenanceRequest r3 = new MaintenanceRequest("MR-003", "RM-006", "STF-003",
                "Air conditioning not working", MaintenanceRequest.Urgency.HIGH);
        r3.setAssignedTo("HVAC Team");
        r3.setStatus(MaintenanceRequest.Status.IN_PROGRESS);
        requests.put(r3.getRequestId(), r3);

        MaintenanceRequest r4 = new MaintenanceRequest("MR-004", "RM-005", "STU-001",
                "Whiteboard markers dried out", MaintenanceRequest.Urgency.LOW);
        r4.setStatus(MaintenanceRequest.Status.COMPLETED);
        requests.put(r4.getRequestId(), r4);

        requestCounter = 4;
    }

    public MaintenanceRequest createRequest(String roomId, String reportedBy,
                                            String description, MaintenanceRequest.Urgency urgency) {
        String requestId = "MR-" + String.format("%03d", ++requestCounter);
        MaintenanceRequest request = new MaintenanceRequest(requestId, roomId, reportedBy, description, urgency);
        requests.put(requestId, request);
        return request;
    }

    public MaintenanceRequest assignRequest(String requestId, String assignedTo) {
        MaintenanceRequest request = requests.get(requestId);
        if (request == null) {
            throw new IllegalArgumentException("Maintenance request not found: " + requestId);
        }
        request.setAssignedTo(assignedTo);
        request.setStatus(MaintenanceRequest.Status.ASSIGNED);
        return request;
    }

    public MaintenanceRequest updateStatus(String requestId, MaintenanceRequest.Status status) {
        MaintenanceRequest request = requests.get(requestId);
        if (request == null) {
            throw new IllegalArgumentException("Maintenance request not found: " + requestId);
        }
        request.setStatus(status);
        return request;
    }

    public MaintenanceRequest findById(String requestId) {
        return requests.get(requestId);
    }

    public List<MaintenanceRequest> getAllRequests() {
        return new ArrayList<>(requests.values());
    }

    public List<MaintenanceRequest> getRequestsByUser(String userId) {
        return requests.values().stream()
                .filter(r -> r.getReportedBy().equals(userId))
                .collect(Collectors.toList());
    }

    public List<MaintenanceRequest> getRequestsByStatus(MaintenanceRequest.Status status) {
        return requests.values().stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Map<MaintenanceRequest.Urgency, Long> getRequestCountByUrgency() {
        return requests.values().stream()
                .collect(Collectors.groupingBy(MaintenanceRequest::getUrgency, Collectors.counting()));
    }

    public Map<MaintenanceRequest.Status, Long> getRequestCountByStatus() {
        return requests.values().stream()
                .collect(Collectors.groupingBy(MaintenanceRequest::getStatus, Collectors.counting()));
    }
}
