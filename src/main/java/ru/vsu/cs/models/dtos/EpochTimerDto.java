package ru.vsu.cs.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpochTimerDto {
    private int id;
    private int currTime;
    private int environment;
}
