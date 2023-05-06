package ru.vsu.cs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.cs.annotations.Column;
import ru.vsu.cs.annotations.ForeignKey;
import ru.vsu.cs.annotations.Id;

@Getter
@Setter
@AllArgsConstructor
public class Boiler implements Model {
    @Id
    private int id;
    @Column(name = "boilerState")
    private BoilerState boilerState;

    public Boiler(int id, BoilerState boilerState) {
        this.id = id;
        this.boilerState = boilerState;
    }

    @Column(name = "environment")
    @ForeignKey(name = "environment_id", refTable = "environment")
    private Integer environment;
}
