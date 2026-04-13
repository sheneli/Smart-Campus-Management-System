package com.scms;

import com.scms.model.Notification;
import com.scms.pattern.behavioural.CampusEventListener;
import com.scms.pattern.behavioural.EventType;
import com.scms.pattern.behavioural.NotificationService;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Notification Service Tests")
class NotificationServiceTest {

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = NotificationService.getInstance();
        notificationService.clearAll();
    }

    // Explanation: Marks the following method as a JUnit test case.
    @Test
    @DisplayName("Should send notification to a user")
    void testSendNotification() {
        notificationService.sendNotification("STU-001", "Test Title",
                "Test Message", Notification.Type.ANNOUNCEMENT);

        List<Notification> notifications = notificationService.getNotifications("STU-001");
        assertFalse(notifications.isEmpty());
        assertEquals("Test Title", notifications.get(0).getTitle());
    }

    @Test
    @DisplayName("Should track unread count correctly")
    void testUnreadCount() {
        notificationService.sendNotification("STU-001", "Notif 1", "Msg 1", Notification.Type.ANNOUNCEMENT);
        notificationService.sendNotification("STU-001", "Notif 2", "Msg 2", Notification.Type.BOOKING_CONFIRMED);

        assertEquals(2, notificationService.getUnreadCount("STU-001"));

        notificationService.markAllAsRead("STU-001");
        assertEquals(0, notificationService.getUnreadCount("STU-001"));
    }

    @Test
    @DisplayName("Should broadcast notifications to multiple users")
    void testBroadcastNotification() {
        List<String> users = List.of("STU-001", "STU-002", "STF-001");
        notificationService.broadcastNotification(users, "Campus Announcement",
                "Building closed tomorrow", Notification.Type.ANNOUNCEMENT);

        for (String userId : users) {
            List<Notification> notifs = notificationService.getNotifications(userId);
            assertFalse(notifs.isEmpty());
        }
    }

    @Test
    @DisplayName("Observer pattern: Should notify subscribed listeners")
    void testObserverPattern() {
        AtomicBoolean eventReceived = new AtomicBoolean(false);

        CampusEventListener listener = (type, title, message) -> {
            eventReceived.set(true);
            assertEquals(EventType.BOOKING_CREATED, type);
        };

        notificationService.subscribe(EventType.BOOKING_CREATED, listener);
        notificationService.publish(EventType.BOOKING_CREATED, "Booking Created", "Room booked");

        assertTrue(eventReceived.get());

        notificationService.unsubscribe(EventType.BOOKING_CREATED, listener);
    }

    @Test
    @DisplayName("Should return empty list for user with no notifications")
    void testEmptyNotifications() {
        List<Notification> notifications = notificationService.getNotifications("NON-EXISTENT");
        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
    }

    @Test
    @DisplayName("Should mark individual notification as read")
    void testMarkAsRead() {
        notificationService.sendNotification("STF-001", "Test", "Message", Notification.Type.MAINTENANCE_UPDATE);
        List<Notification> notifs = notificationService.getNotifications("STF-001");

        assertFalse(notifs.get(0).isRead());
        notifs.get(0).markAsRead();
        assertTrue(notifs.get(0).isRead());
    }
}
