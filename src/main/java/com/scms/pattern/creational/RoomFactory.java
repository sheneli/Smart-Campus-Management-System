package com.scms.pattern.creational;

import com.scms.model.Room;
import com.scms.model.RoomType;

import java.util.Arrays;
import java.util.List;

/**
 * Factory Pattern (Creational) - Creates different types of rooms with
 * pre-configured equipment based on the room type.
 */
public class RoomFactory {

    private static int roomCounter = 100;

    public static Room createRoom(String name, RoomType type, int capacity) {
        String roomId = "RM-" + (++roomCounter);
        List<String> equipment = getDefaultEquipment(type);
        return new Room(roomId, name, type, capacity, equipment);
    }

    public static Room createRoom(String roomId, String name, RoomType type,
                                  int capacity, List<String> equipment) {
        return new Room(roomId, name, type, capacity, equipment);
    }

    private static List<String> getDefaultEquipment(RoomType type) {
        return switch (type) {
            case LECTURE_HALL -> Arrays.asList("Projector", "Whiteboard", "Microphone", "Speakers");
            case COMPUTER_LAB -> Arrays.asList("Computers", "Projector", "Whiteboard", "Printers");
            case SEMINAR_ROOM -> Arrays.asList("Projector", "Whiteboard", "Video Conferencing");
            case MEETING_ROOM -> Arrays.asList("Whiteboard", "Video Conferencing", "Display Screen");
            case AUDITORIUM -> Arrays.asList("Projector", "Microphone", "Speakers", "Stage Lighting", "Recording Equipment");
        };
    }
}
