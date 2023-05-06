package ru.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.cs.annotations.Column;
import ru.vsu.cs.annotations.ForeignKey;
import ru.vsu.cs.annotations.Id;

@AllArgsConstructor
@Getter
@Setter
public class Room implements Model {
    @Id
    private int id;
    @Column(name = "valveState")
    private ValveState valveState;
    @Column(name = "currTemp")
    private float currTemp;
    @Column(name = "infraredSensor")
    private boolean infraredSensor;
    @Column(name = "workingTemp")
    private int workingTemp;
    @Column(name = "waitingTemp")
    private int waitingTemp;
    @Column(name = "batterySquare")
    private int batterySquare;

    public Room(int id, ValveState valveState, float currTemp, boolean infraredSensor, int workingTemp, int waitingTemp, int batterySquare) {
        this.id = id;
        this.valveState = valveState;
        this.currTemp = currTemp;
        this.infraredSensor = infraredSensor;
        this.workingTemp = workingTemp;
        this.waitingTemp = waitingTemp;
        this.batterySquare = batterySquare;
    }

    @Column(name = "environment")
    @ForeignKey(name = "environment_id", refTable = "environment")
    private int environment;
}
