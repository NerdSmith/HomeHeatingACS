package ru.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.cs.annotations.Column;
import ru.vsu.cs.annotations.Id;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Environment implements Model {
    @Id
    private int id;
    @Column(name = "temp")
    private int temp;

    public Environment(int id, int temp) {
        this.id = id;
        this.temp = temp;
    }

    private EpochTimer epochTimer;
//    @OneToMany(targetClass = Boiler.class, mappedBy = "parent")
    private Boiler boiler;
    private List<Room> rooms;

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
