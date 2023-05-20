package ru.vsu.cs.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private int id;
    private int valveState;
    private float currTemp;
    private boolean infraredSensor;
    private int workingTemp;
    private int waitingTemp;
    private int batterySquare;
    private int environment;
}
