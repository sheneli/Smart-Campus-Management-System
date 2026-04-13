package com.scms;

import com.scms.exception.BookingException;
import com.scms.exception.InvalidBookingException;
import com.scms.exception.RoomNotFoundException;
import com.scms.model.Booking;
import com.scms.model.Room;
import com.scms.model.RoomType;
import com.scms.service.BookingService;
import com.scms.service.RoomService;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// Explanation: Imports static members from `org.junit.jupiter.api.Assertions.*` for direct use in this file.
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Booking Service Tests")
class BookingServiceTest {

    private RoomService roomService;
    private BookingService bookingService;

    // Marks the following method to run before each test.
    @BeforeEach
    void setUp() {
        roomService = new RoomService();
        bookingService = new BookingService(roomService);
    }

    @Test
    @DisplayName("Should create a valid booking successfully")
    void testCreateValidBooking() {
        LocalDate date = LocalDate.now().plusDays(10);
        Booking booking = bookingService.createBooking("RM-001", "STU-001", date,
                LocalTime.of(8, 0), LocalTime.of(9, 0), "Test Booking");

        // Asserts that the tested value is not null.
        assertNotNull(booking);
        assertEquals("RM-001", booking.getRoomId());
        assertEquals("STU-001", booking.getUserId());
        assertEquals(Booking.Status.CONFIRMED, booking.getStatus());
    }

    @Test
    @DisplayName("Should prevent double-booking of the same room and time slot")
    void testPreventDoubleBooking() {
        // Explanation: Declares and initializes `date` as `LocalDate`.
        LocalDate date = LocalDate.now().plusDays(15);
        bookingService.createBooking("RM-002", "STF-001", date,
                LocalTime.of(10, 0), LocalTime.of(12, 0), "First Booking");

        // Asserts that this operation throws the expected exception.
        assertThrows(BookingException.class, () ->
                bookingService.createBooking("RM-002", "STU-002", date,
                        LocalTime.of(11, 0), LocalTime.of(13, 0), "Overlapping Booking"));
    }

    @Test
    // Explanation: Provides a readable JUnit display name for the following test element.
    @DisplayName("Should allow booking different rooms at the same time")
    void testBookDifferentRoomsAtSameTime() {
        LocalDate date = LocalDate.now().plusDays(20);
        Booking b1 = bookingService.createBooking("RM-001", "STF-001", date,
                LocalTime.of(10, 0), LocalTime.of(12, 0), "Room 1 Booking");
        Booking b2 = bookingService.createBooking("RM-002", "STF-002", date,
                LocalTime.of(10, 0), LocalTime.of(12, 0), "Room 2 Booking");

        // Asserts that the tested value is not null.
        assertNotNull(b1);
        assertNotNull(b2);
        assertNotEquals(b1.getRoomId(), b2.getRoomId());
    }

    @Test
    @DisplayName("Should cancel a booking successfully")
    void testCancelBooking() {
        LocalDate date = LocalDate.now().plusDays(12);
        Booking booking = bookingService.createBooking("RM-005", "STU-001", date,
                LocalTime.of(14, 0), LocalTime.of(16, 0), "To Cancel");

        bookingService.cancelBooking(booking.getBookingId());

        assertEquals(Booking.Status.CANCELLED, booking.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when cancelling already cancelled booking")
    void testCancelAlreadyCancelledBooking() {
        // Declares and initializes `date` as `LocalDate`.
        LocalDate date = LocalDate.now().plusDays(13);
        Booking booking = bookingService.createBooking("RM-006", "STU-002", date,
                LocalTime.of(9, 0), LocalTime.of(10, 0), "Cancel Test");

        bookingService.cancelBooking(booking.getBookingId());

        assertThrows(InvalidBookingException.class, () ->
                bookingService.cancelBooking(booking.getBookingId()));
    }

    @Test
    @DisplayName("Should throw exception for invalid time range (start after end)")
    void testInvalidTimeRange() {
        LocalDate date = LocalDate.now().plusDays(14);
        assertThrows(InvalidBookingException.class, () ->
                bookingService.createBooking("RM-001", "STU-001", date,
                        LocalTime.of(14, 0), LocalTime.of(10, 0), "Invalid Time"));
    }

    @Test
    @DisplayName("Should throw exception for booking in the past")
    void testBookingInThePast() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        assertThrows(InvalidBookingException.class, () ->
                bookingService.createBooking("RM-001", "STU-001", pastDate,
                        LocalTime.of(10, 0), LocalTime.of(12, 0), "Past Booking"));
    }

    @Test
    @DisplayName("Should throw exception for non-existent room")
    void testBookingNonExistentRoom() {
        LocalDate date = LocalDate.now().plusDays(5);
        assertThrows(RoomNotFoundException.class, () ->
                bookingService.createBooking("INVALID-ROOM", "STU-001", date,
                        LocalTime.of(10, 0), LocalTime.of(12, 0), "Invalid Room"));
    }

    @Test
    @DisplayName("Should retrieve bookings for a specific user")
    void testGetBookingsForUser() {
        // Declares and initializes `bookings` as `List<Booking>`.
        List<Booking> bookings = bookingService.getBookingsForUser("STF-001");
        assertNotNull(bookings);
        assertTrue(bookings.stream().allMatch(b -> b.getUserId().equals("STF-001")));
    }

    @Test
    @DisplayName("Should identify the most booked room")
    void testMostBookedRoom() {
        String mostBooked = bookingService.getMostBookedRoomId();
        assertNotNull(mostBooked);
        assertNotEquals("N/A", mostBooked);
    }

    @Test
    @DisplayName("Should allow booking after cancellation of existing booking in same slot")
    void testBookAfterCancellation() {
        LocalDate date = LocalDate.now().plusDays(25);
        Booking first = bookingService.createBooking("RM-007", "STF-001", date,
                LocalTime.of(10, 0), LocalTime.of(12, 0), "First");

        bookingService.cancelBooking(first.getBookingId());

        Booking second = bookingService.createBooking("RM-007", "STU-001", date,
                LocalTime.of(10, 0), LocalTime.of(12, 0), "After Cancel");

        assertNotNull(second);
        assertEquals(Booking.Status.CONFIRMED, second.getStatus());
    }
}
