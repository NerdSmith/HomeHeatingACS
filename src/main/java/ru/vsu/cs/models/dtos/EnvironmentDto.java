package ru.vsu.cs.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnvironmentDto {
    private int id;
    private int temp;
//    private int epochTimer;
//    private int boiler;
//    private List<Integer> rooms;
}
