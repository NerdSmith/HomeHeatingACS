package ru.vsu.cs.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EpochTimerDto {
    private int id;
    private int currTime;
    private int environment;
}
