package com.scms.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class MaintenanceRequest {
    public enum Urgency { LOW, MEDIUM, HIGH, CRITICAL }
    public enum Status { PENDING, ASSIGNED, IN_PROGRESS, COMPLETED }

    private final String requestId;
    private final String roomId;
    private final String reportedBy;
    private String description;
    private Urgency urgency;
    private Status status;
    private String assignedTo;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MaintenanceRequest(String requestId, String roomId, String reportedBy,
                              String description, Urgency urgency) {
        this.requestId = requestId;
        this.roomId = roomId;
        this.reportedBy = reportedBy;
        this.description = description;
        this.urgency = urgency;
        this.status = Status.PENDING;
        this.assignedTo = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // Defines a getter that returns `requestId`.
    public String getRequestId() { return requestId; }
    public String getRoomId() { return roomId; }
    public String getReportedBy() { return reportedBy; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Urgency getUrgency() { return urgency; }
    public void setUrgency(Urgency urgency) { this.urgency = urgency; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        this.updatedAt = LocalDateTime.now();
    }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaintenanceRequest that = (MaintenanceRequest) o;
        return Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

    @Override
    public String toString() {
        return "MR-" + requestId + " [" + urgency + "] " + description;
    }
}
