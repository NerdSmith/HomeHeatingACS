package ru.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Boiler {
    private int id;
    private BoilerState boilerState;
}
