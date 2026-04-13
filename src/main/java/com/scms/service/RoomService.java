package com.scms.service;

import com.scms.exception.DuplicateDataException;
import com.scms.exception.RoomNotFoundException;
import com.scms.model.Room;
import com.scms.model.RoomType;
import com.scms.pattern.creational.RoomFactory;

import java.util.*;
import java.util.stream.Collectors;

public class RoomService {

    private final Map<String, Room> rooms = new LinkedHashMap<>();

    public RoomService() {
        initializeDefaultRooms();
    }

    private void initializeDefaultRooms() {
        addRoom(RoomFactory.createRoom("RM-001", "Main Lecture Hall A", RoomType.LECTURE_HALL, 200,
                Arrays.asList("Projector", "Microphone", "Speakers", "Whiteboard")));
        addRoom(RoomFactory.createRoom("RM-002", "Lecture Hall B", RoomType.LECTURE_HALL, 150,
                Arrays.asList("Projector", "Whiteboard", "Speakers")));
        addRoom(RoomFactory.createRoom("RM-003", "Computer Lab 1", RoomType.COMPUTER_LAB, 40,
                Arrays.asList("30 PCs", "Projector", "Printer", "Whiteboard")));
        addRoom(RoomFactory.createRoom("RM-004", "Computer Lab 2", RoomType.COMPUTER_LAB, 35,
                Arrays.asList("25 PCs", "Projector", "Scanner")));
        addRoom(RoomFactory.createRoom("RM-005", "Seminar Room 1", RoomType.SEMINAR_ROOM, 30,
                Arrays.asList("Projector", "Whiteboard", "Video Conferencing")));
        addRoom(RoomFactory.createRoom("RM-006", "Meeting Room A", RoomType.MEETING_ROOM, 12,
                Arrays.asList("Display Screen", "Whiteboard", "Video Conferencing")));
        addRoom(RoomFactory.createRoom("RM-007", "Meeting Room B", RoomType.MEETING_ROOM, 8,
                Arrays.asList("Display Screen", "Whiteboard")));
        addRoom(RoomFactory.createRoom("RM-008", "Grand Auditorium", RoomType.AUDITORIUM, 500,
                Arrays.asList("Projector", "Microphone", "Speakers", "Stage Lighting", "Recording Equipment")));
    }

    private void addRoom(Room room) {
        rooms.put(room.getRoomId(), room);
    }

    public Room addRoom(String name, RoomType type, int capacity, List<String> equipment) {
        Room room = RoomFactory.createRoom(name, type, capacity);
        if (equipment != null && !equipment.isEmpty()) {
            room.setEquipment(equipment);
        }
        if (rooms.containsKey(room.getRoomId())) {
            throw new DuplicateDataException("Room already exists: " + room.getRoomId());
        }
        rooms.put(room.getRoomId(), room);
        return room;
    }

    public void updateRoom(String roomId, String name, RoomType type, int capacity, List<String> equipment) {
        Room room = findRoomById(roomId);
        if (room == null) {
            throw new RoomNotFoundException(roomId);
        }
        room.setName(name);
        room.setType(type);
        room.setCapacity(capacity);
        if (equipment != null) {
            room.setEquipment(equipment);
        }
    }

    public void deactivateRoom(String roomId) {
        Room room = findRoomById(roomId);
        if (room == null) {
            throw new RoomNotFoundException(roomId);
        }
        room.setActive(false);
    }

    public void activateRoom(String roomId) {
        Room room = findRoomById(roomId);
        if (room == null) {
            throw new RoomNotFoundException(roomId);
        }
        room.setActive(true);
    }

    public Room findRoomById(String roomId) {
        return rooms.get(roomId);
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    public List<Room> getActiveRooms() {
        return rooms.values().stream()
                .filter(Room::isActive)
                .collect(Collectors.toList());
    }

    public List<Room> getRoomsByType(RoomType type) {
        return rooms.values().stream()

                .filter(r -> r.getType() == type && r.isActive())
                .collect(Collectors.toList());
    }

    public int getRoomCount() {
        return rooms.size();
    }
}
