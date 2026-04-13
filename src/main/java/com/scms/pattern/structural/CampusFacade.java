package com.scms.pattern.structural;

import com.scms.exception.*;
import com.scms.model.*;
import com.scms.pattern.behavioural.NotificationService;
import com.scms.pattern.creational.SystemManager;
import com.scms.service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Facade Pattern (Structural) - Provides a simplified interface to the
 * complex subsystem of services, hiding internal complexity from the GUI layer.
 */
public class CampusFacade {

    private final SystemManager systemManager;
    private final NotificationService notificationService;

    public CampusFacade() {
        this.systemManager = SystemManager.getInstance();
        this.notificationService = NotificationService.getInstance();
    }


    public User login(String email, String password) {
        User user = systemManager.getUserService().authenticate(email, password);
        if (user != null) {
            systemManager.setCurrentUser(user);
        }
        return user;
    }

    public void logout() {
        systemManager.logout();
    }

    public User getCurrentUser() {
        return systemManager.getCurrentUser();
    }


    public List<Room> getAllRooms() {
        return systemManager.getRoomService().getAllRooms();
    }

    public List<Room> getActiveRooms() {
        return systemManager.getRoomService().getActiveRooms();
    }

    public Room addRoom(String name, RoomType type, int capacity, List<String> equipment) {
        User current = systemManager.getCurrentUser();
        if (current == null || current.getRole() != UserRole.ADMINISTRATOR) {
            throw new UnauthorizedActionException("Only administrators can add rooms.");
        }
        return systemManager.getRoomService().addRoom(name, type, capacity, equipment);
    }

    public void updateRoom(String roomId, String name, RoomType type, int capacity, List<String> equipment) {
        User current = systemManager.getCurrentUser();
        if (current == null || current.getRole() != UserRole.ADMINISTRATOR) {
            throw new UnauthorizedActionException("Only administrators can edit rooms.");
        }
        systemManager.getRoomService().updateRoom(roomId, name, type, capacity, equipment);
    }

    public void deactivateRoom(String roomId) {
        User current = systemManager.getCurrentUser();
        if (current == null || current.getRole() != UserRole.ADMINISTRATOR) {
            throw new UnauthorizedActionException("Only administrators can deactivate rooms.");
        }
        systemManager.getRoomService().deactivateRoom(roomId);
    }

    public void activateRoom(String roomId) {
        User current = systemManager.getCurrentUser();
        if (current == null || current.getRole() != UserRole.ADMINISTRATOR) {
            throw new UnauthorizedActionException("Only administrators can activate rooms.");
        }
        systemManager.getRoomService().activateRoom(roomId);
    }


    public Booking createBooking(String roomId, LocalDate date, LocalTime startTime,
                                 LocalTime endTime, String purpose) {
        User current = systemManager.getCurrentUser();
        if (current == null) {
            throw new UnauthorizedActionException("You must be logged in to make a booking.");
        }
        Booking booking = systemManager.getBookingService().createBooking(
                roomId, current.getUserId(), date, startTime, endTime, purpose);

        Room room = systemManager.getRoomService().findRoomById(roomId);
        String roomName = room != null ? room.getName() : roomId;
        notificationService.sendNotification(current.getUserId(),
                "Booking Confirmed",
                "Your booking for " + roomName + " on " + date + " (" + startTime + "-" + endTime + ") is confirmed.",
                Notification.Type.BOOKING_CONFIRMED);

        return booking;
    }

    public void cancelBooking(String bookingId) {
        User current = systemManager.getCurrentUser();
        if (current == null) {
            throw new UnauthorizedActionException("You must be logged in to cancel a booking.");
        }
        Booking booking = systemManager.getBookingService().findBookingById(bookingId);
        if (booking == null) {
            throw new InvalidBookingException("Booking not found: " + bookingId);
        }
        if (!booking.getUserId().equals(current.getUserId()) && current.getRole() != UserRole.ADMINISTRATOR) {
            throw new UnauthorizedActionException("You can only cancel your own bookings.");
        }
        systemManager.getBookingService().cancelBooking(bookingId);

        notificationService.sendNotification(booking.getUserId(),
                "Booking Cancelled",
                "Your booking " + bookingId + " has been cancelled.",
                Notification.Type.BOOKING_CANCELLED);
    }

    public List<Booking> getAllBookings() {
        return systemManager.getBookingService().getAllBookings();
    }

    public List<Booking> getBookingsForUser(String userId) {
        return systemManager.getBookingService().getBookingsForUser(userId);
    }

    public List<Booking> getBookingsForRoom(String roomId) {
        return systemManager.getBookingService().getBookingsForRoom(roomId);
    }


    public MaintenanceRequest createMaintenanceRequest(String roomId, String description,
                                                       MaintenanceRequest.Urgency urgency) {
        User current = systemManager.getCurrentUser();
        if (current == null) {
            throw new UnauthorizedActionException("You must be logged in to report maintenance issues.");
        }
        MaintenanceRequest request = systemManager.getMaintenanceService().createRequest(
                roomId, current.getUserId(), description, urgency);

        List<User> admins = systemManager.getUserService().getUsersByRole(UserRole.ADMINISTRATOR);
        for (User admin : admins) {
            notificationService.sendNotification(admin.getUserId(),
                    "New Maintenance Request",
                    "Issue reported in room " + roomId + ": " + description,
                    Notification.Type.MAINTENANCE_UPDATE);
        }

        return request;
    }

    public void assignMaintenanceRequest(String requestId, String assignedTo) {
        User current = systemManager.getCurrentUser();
        if (current == null || current.getRole() != UserRole.ADMINISTRATOR) {
            throw new UnauthorizedActionException("Only administrators can assign maintenance tasks.");
        }
        MaintenanceRequest request = systemManager.getMaintenanceService().assignRequest(requestId, assignedTo);

        notificationService.sendNotification(request.getReportedBy(),
                "Maintenance Update",
                "Your maintenance request " + requestId + " has been assigned.",
                Notification.Type.MAINTENANCE_UPDATE);
    }

    public void updateMaintenanceStatus(String requestId, MaintenanceRequest.Status status) {
        User current = systemManager.getCurrentUser();
        if (current == null || current.getRole() != UserRole.ADMINISTRATOR) {
            throw new UnauthorizedActionException("Only administrators can update maintenance status.");
        }
        MaintenanceRequest request = systemManager.getMaintenanceService().updateStatus(requestId, status);

        notificationService.sendNotification(request.getReportedBy(),
                "Maintenance Status Update",
                "Request " + requestId + " status changed to: " + status,
                Notification.Type.MAINTENANCE_UPDATE);
    }

    public List<MaintenanceRequest> getAllMaintenanceRequests() {
        return systemManager.getMaintenanceService().getAllRequests();
    }

    public List<MaintenanceRequest> getMaintenanceRequestsByUser(String userId) {
        return systemManager.getMaintenanceService().getRequestsByUser(userId);
    }


    public List<Notification> getNotificationsForCurrentUser() {
        User current = systemManager.getCurrentUser();
        if (current == null) return List.of();
        return notificationService.getNotifications(current.getUserId());
    }

    public int getUnreadNotificationCount() {
        User current = systemManager.getCurrentUser();
        if (current == null) return 0;
        return notificationService.getUnreadCount(current.getUserId());
    }

    public void markAllNotificationsRead() {
        User current = systemManager.getCurrentUser();
        if (current != null) {
            notificationService.markAllAsRead(current.getUserId());
        }
    }


    public List<User> getAllUsers() {
        return systemManager.getUserService().getAllUsers();
    }

    public String getMostBookedRoom() {
        return systemManager.getBookingService().getMostBookedRoomId();
    }

    public int getTotalBookingsCount() {
        return systemManager.getBookingService().getAllBookings().size();
    }

    public int getActiveMaintenanceCount() {
        return (int) systemManager.getMaintenanceService().getAllRequests().stream()
                .filter(r -> r.getStatus() != MaintenanceRequest.Status.COMPLETED)
                .count();
    }
}
