package ru.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Environment {
    private int id;
    private int temp;
    private EpochTimer epochTimer;
    private Boiler boiler;
    private List<Room> rooms;

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
