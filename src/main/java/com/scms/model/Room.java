package com.scms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Room {
    private final String roomId;
    private String name;
    private RoomType type;
    private int capacity;
    private List<String> equipment;
    private boolean active;

    public Room(String roomId, String name, RoomType type, int capacity, List<String> equipment) {
        this.roomId = roomId;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.equipment = new ArrayList<>(equipment);
        this.active = true;
    }

    public String getRoomId() { return roomId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public RoomType getType() { return type; }
    public void setType(RoomType type) { this.type = type; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public List<String> getEquipment() { return Collections.unmodifiableList(equipment); }
    public void setEquipment(List<String> equipment) { this.equipment = new ArrayList<>(equipment); }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public String toString() {
        return name + " (" + type.getDisplayName() + ", Cap: " + capacity + ")";
    }
}
