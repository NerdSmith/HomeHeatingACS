package ru.vsu.cs.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.cs.models.BoilerState;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoilerDto {
    private int id;
    private int boilerState;
    private int environment;
}
