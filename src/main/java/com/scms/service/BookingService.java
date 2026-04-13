package com.scms.service;

import com.scms.exception.BookingException;
import com.scms.exception.InvalidBookingException;
import com.scms.exception.RoomNotFoundException;
import com.scms.model.Booking;
import com.scms.model.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class BookingService {

    private final Map<String, Booking> bookings = new LinkedHashMap<>();
    private final RoomService roomService;
    private int bookingCounter = 0;

    public BookingService(RoomService roomService) {
        this.roomService = roomService;
        initializeDefaultBookings();
    }

    private void initializeDefaultBookings() {
        LocalDate today = LocalDate.now();

        createBookingInternal("RM-001", "STF-001", today.plusDays(1),
                LocalTime.of(9, 0), LocalTime.of(11, 0), "Advanced Programming Lecture");
        createBookingInternal("RM-003", "STF-002", today.plusDays(1),
                LocalTime.of(14, 0), LocalTime.of(16, 0), "Engineering Workshop");
        createBookingInternal("RM-005", "STF-003", today.plusDays(2),
                LocalTime.of(10, 0), LocalTime.of(12, 0), "Mathematics Seminar");
        createBookingInternal("RM-006", "ADM-001", today.plusDays(2),
                LocalTime.of(13, 0), LocalTime.of(14, 0), "Staff Meeting");
        createBookingInternal("RM-002", "STU-001", today.plusDays(3),
                LocalTime.of(15, 0), LocalTime.of(17, 0), "Study Group Session");
    }

    private void createBookingInternal(String roomId, String userId, LocalDate date,
                                       LocalTime startTime, LocalTime endTime, String purpose) {
        String bookingId = "BK-" + String.format("%04d", ++bookingCounter);
        Booking booking = new Booking(bookingId, roomId, userId, date, startTime, endTime, purpose);
        bookings.put(bookingId, booking);
    }

    public Booking createBooking(String roomId, String userId, LocalDate date,
                                 LocalTime startTime, LocalTime endTime, String purpose) {
        Room room = roomService.findRoomById(roomId);
        if (room == null) {
            throw new RoomNotFoundException(roomId);
        }
        if (!room.isActive()) {
            throw new InvalidBookingException("Room " + roomId + " is not available for booking.");
        }

        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new InvalidBookingException("Start time must be before end time.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new InvalidBookingException("Cannot book a room in the past.");
        }

        String bookingId = "BK-" + String.format("%04d", ++bookingCounter);
        Booking newBooking = new Booking(bookingId, roomId, userId, date, startTime, endTime, purpose);

        for (Booking existing : bookings.values()) {
            if (existing.overlapsWith(newBooking)) {
                throw new BookingException("Room " + roomId + " is already booked for the selected time slot.");
            }
        }

        bookings.put(bookingId, newBooking);
        return newBooking;
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new InvalidBookingException("Booking not found: " + bookingId);
        }
        if (booking.getStatus() == Booking.Status.CANCELLED) {
            throw new InvalidBookingException("Booking is already cancelled.");
        }
        booking.setStatus(Booking.Status.CANCELLED);
    }

    public Booking findBookingById(String bookingId) {
        return bookings.get(bookingId);
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings.values());
    }

    public List<Booking> getBookingsForUser(String userId) {
        return bookings.values().stream()
                .filter(b -> b.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Booking> getBookingsForRoom(String roomId) {
        return bookings.values().stream()
                .filter(b -> b.getRoomId().equals(roomId))
                .collect(Collectors.toList());
    }

    public List<Booking> getActiveBookingsForRoom(String roomId) {
        return bookings.values().stream()
                .filter(b -> b.getRoomId().equals(roomId) && b.getStatus() != Booking.Status.CANCELLED)
                .collect(Collectors.toList());
    }

    public String getMostBookedRoomId() {
        return bookings.values().stream()
                .filter(b -> b.getStatus() != Booking.Status.CANCELLED)
                .collect(Collectors.groupingBy(Booking::getRoomId, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public Map<String, Long> getBookingCountByRoom() {
        return bookings.values().stream()
                .filter(b -> b.getStatus() != Booking.Status.CANCELLED)
                .collect(Collectors.groupingBy(Booking::getRoomId, Collectors.counting()));
    }
}
