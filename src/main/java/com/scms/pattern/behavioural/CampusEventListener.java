package com.scms.pattern.behavioural;

/**
 * Observer Pattern (Behavioural) - Listener interface for campus events.
 */
public interface CampusEventListener {
    void onEvent(EventType eventType, String title, String message);
}
