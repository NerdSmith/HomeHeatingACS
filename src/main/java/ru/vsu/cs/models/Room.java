package ru.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Room {
    private int id;
    private ValveState valveState;
    private float currTemp;
    private boolean infraredSensor;
    private int workingTemp;
    private int waitingTemp;
    private int batterySquare;

}
