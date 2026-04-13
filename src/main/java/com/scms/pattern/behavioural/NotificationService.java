package com.scms.pattern.behavioural;

import com.scms.model.Notification;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Observer Pattern (Behavioural) - Manages event subscriptions and
 * notification delivery to registered listeners/users.
 */
public class NotificationService {

    private static final NotificationService INSTANCE = new NotificationService();

    private final Map<EventType, List<CampusEventListener>> listeners = new ConcurrentHashMap<>();
    private final Map<String, List<Notification>> userNotifications = new ConcurrentHashMap<>();
    private int notificationCounter = 0;

    private NotificationService() {
        for (EventType type : EventType.values()) {
            listeners.put(type, new CopyOnWriteArrayList<>());
        }
    }

    public static NotificationService getInstance() {
        return INSTANCE;
    }

    public void subscribe(EventType eventType, CampusEventListener listener) {
        listeners.get(eventType).add(listener);
    }

    public void unsubscribe(EventType eventType, CampusEventListener listener) {
        listeners.get(eventType).remove(listener);
    }

    public void publish(EventType eventType, String title, String message) {
        List<CampusEventListener> eventListeners = listeners.get(eventType);
        for (CampusEventListener listener : eventListeners) {
            listener.onEvent(eventType, title, message);
        }
    }

    public void sendNotification(String userId, String title, String message, Notification.Type type) {
        String notifId = "NTF-" + (++notificationCounter);
        Notification notification = new Notification(notifId, userId, title, message, type);
        userNotifications.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>()).add(notification);
    }

    public void broadcastNotification(Collection<String> userIds, String title,
                                      String message, Notification.Type type) {
        for (String userId : userIds) {
            sendNotification(userId, title, message, type);
        }
    }

    public List<Notification> getNotifications(String userId) {
        return userNotifications.getOrDefault(userId, Collections.emptyList());
    }

    public List<Notification> getUnreadNotifications(String userId) {
        return getNotifications(userId).stream()
                .filter(n -> !n.isRead())

                .collect(Collectors.toList());
    }

    public int getUnreadCount(String userId) {
        return (int) getNotifications(userId).stream().filter(n -> !n.isRead()).count();
    }

    public void markAllAsRead(String userId) {
        getNotifications(userId).forEach(Notification::markAsRead);
    }

    public void clearAll() {
        userNotifications.clear();
        notificationCounter = 0;
    }
}
