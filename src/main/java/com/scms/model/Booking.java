package com.scms.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Booking {
    public enum Status { CONFIRMED, CANCELLED, PENDING }

    private final String bookingId;
    private final String roomId;
    private final String userId;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private String purpose;
    private Status status;

    // Starts the signature for the `Booking` method.
    public Booking(String bookingId, String roomId, String userId, LocalDate date,
                   LocalTime startTime, LocalTime endTime, String purpose) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.userId = userId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.status = Status.CONFIRMED;
    }

    public String getBookingId() { return bookingId; }
    public String getRoomId() { return roomId; }
    public String getUserId() { return userId; }
    public LocalDate getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public boolean overlapsWith(Booking other) {
        if (!this.roomId.equals(other.roomId)) return false;
        if (!this.date.equals(other.date)) return false;
        if (this.status == Status.CANCELLED || other.status == Status.CANCELLED) return false;
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }

    @Override
    public String toString() {
        return "Booking " + bookingId + " [" + roomId + "] " + date + " " + startTime + "-" + endTime;
    }
}
