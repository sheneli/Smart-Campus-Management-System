package com.scms.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notification {
    public enum Type { BOOKING_CONFIRMED, BOOKING_CANCELLED, MAINTENANCE_UPDATE, ANNOUNCEMENT }

    private final String notificationId;
    private final String recipientId;
    private final String title;
    private final String message;
    private final Type type;
    private final LocalDateTime timestamp;
    private boolean read;

    public Notification(String notificationId, String recipientId, String title,
                        String message, Type type) {
        this.notificationId = notificationId;
        this.recipientId = recipientId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.read = false;
    }

    public String getNotificationId() { return notificationId; }
    public String getRecipientId() { return recipientId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public Type getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isRead() { return read; }
    public void markAsRead() { this.read = true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId);
    }

    @Override
    public String toString() {
        return (read ? "" : "[NEW] ") + title + " - " + message;
    }
}
