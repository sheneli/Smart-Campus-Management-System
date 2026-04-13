package com.scms.model;

public enum RoomType {
    LECTURE_HALL("Lecture Hall"),
    COMPUTER_LAB("Computer Lab"),
    SEMINAR_ROOM("Seminar Room"),
    MEETING_ROOM("Meeting Room"),
    AUDITORIUM("Auditorium");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
