package com.scms;

import com.scms.exception.*;
import com.scms.model.*;
import com.scms.pattern.creational.SystemManager;
import com.scms.pattern.structural.CampusFacade;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exception Handling Tests")
class ExceptionHandlingTest {

    // Declares the private field `facade` of type `CampusFacade`.
    private CampusFacade facade;

    @BeforeEach
    void setUp() {
        SystemManager.resetInstance();
        facade = new CampusFacade();
    }

    @Test
    @DisplayName("Should throw UnauthorizedActionException when non-admin adds room")
    void testUnauthorizedRoomAddition() {
        facade.login("alex.thompson@student.cardiffmet.ac.uk", "student123");

        assertThrows(UnauthorizedActionException.class, () ->
                facade.addRoom("Test Room", RoomType.MEETING_ROOM, 10, List.of("Whiteboard")));
    }

    @Test
    @DisplayName("Should throw UnauthorizedActionException when not logged in")
    void testBookingWithoutLogin() {
        assertThrows(UnauthorizedActionException.class, () ->
                facade.createBooking("RM-001", LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0), LocalTime.of(12, 0), "Test"));
    }

    @Test
    @DisplayName("Should throw UnauthorizedActionException for student assigning maintenance")
    void testStudentCannotAssignMaintenance() {
        facade.login("alex.thompson@student.cardiffmet.ac.uk", "student123");

        assertThrows(UnauthorizedActionException.class, () ->
                facade.assignMaintenanceRequest("MR-001", "Team A"));
    }

    @Test
    @DisplayName("EXPECTED FAILURE: Should throw exception when booking deactivated room")
    void testBookingDeactivatedRoom() {
        facade.login("admin@cardiffmet.ac.uk", "admin123");
        facade.deactivateRoom("RM-007");

        assertThrows(InvalidBookingException.class, () ->
                facade.createBooking("RM-007", LocalDate.now().plusDays(5),
                        LocalTime.of(10, 0), LocalTime.of(12, 0), "Should Fail"));
    }

    @Test
    @DisplayName("Should throw RoomNotFoundException for invalid room ID")
    void testRoomNotFound() {
        facade.login("admin@cardiffmet.ac.uk", "admin123");

        assertThrows(RoomNotFoundException.class, () ->
                facade.updateRoom("INVALID-ID", "Name", RoomType.MEETING_ROOM, 10, List.of()));
    }

    @Test
    @DisplayName("Should throw DuplicateDataException for duplicate user")
    void testDuplicateUser() {
        assertThrows(DuplicateDataException.class, () ->
                SystemManager.getInstance().getUserService().addUser(
                        new Student("STU-001", "Duplicate", "dup@test.com", "pass", "BSc IT", 1)));
    }

    @Test
    @DisplayName("Should return null for invalid login credentials")
    void testInvalidLogin() {
        User user = facade.login("wrong@email.com", "wrongpassword");
        assertNull(user);
    }

    @Test
    @DisplayName("Student should not be able to cancel another user's booking")
    void testCannotCancelOtherBooking() {
        facade.login("admin@cardiffmet.ac.uk", "admin123");
        facade.createBooking("RM-006", LocalDate.now().plusDays(30),
                LocalTime.of(10, 0), LocalTime.of(11, 0), "Admin Meeting");

        var adminBookings = facade.getBookingsForUser("ADM-001");
        String bookingId = adminBookings.get(adminBookings.size() - 1).getBookingId();

        facade.logout();
        facade.login("alex.thompson@student.cardiffmet.ac.uk", "student123");

        assertThrows(UnauthorizedActionException.class, () ->
                facade.cancelBooking(bookingId));
    }
}
