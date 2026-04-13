package com.scms.pattern.creational;

import com.scms.model.*;
import com.scms.service.BookingService;
import com.scms.service.MaintenanceService;
import com.scms.service.RoomService;
import com.scms.service.UserService;

/**
 * Singleton Pattern (Creational) - Ensures only one instance of the
 * central system manager exists throughout the application lifecycle.
 */
public class SystemManager {

    private static volatile SystemManager instance;

    private final UserService userService;
    private final RoomService roomService;
    private final BookingService bookingService;
    private final MaintenanceService maintenanceService;

    private User currentUser;

    private SystemManager() {
        this.userService = new UserService();
        this.roomService = new RoomService();
        this.bookingService = new BookingService(roomService);
        this.maintenanceService = new MaintenanceService();
        this.currentUser = null;
    }

    public static SystemManager getInstance() {
        if (instance == null) {
            synchronized (SystemManager.class) {
                if (instance == null) {
                    instance = new SystemManager();
                }
            }
        }
        return instance;
    }

    public UserService getUserService() { return userService; }
    public RoomService getRoomService() { return roomService; }
    public BookingService getBookingService() { return bookingService; }
    public MaintenanceService getMaintenanceService() { return maintenanceService; }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User user) { this.currentUser = user; }

    public void logout() { this.currentUser = null; }

    public static void resetInstance() {
        instance = null;
    }
}
