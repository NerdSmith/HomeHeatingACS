package ru.vsu.cs.models;

import java.util.HashMap;
import java.util.Map;

public enum BoilerState {
    ON(1),
    OFF(0);

    private final int value;

    private static final Map<Integer, BoilerState> _map = new HashMap<>();
    static
    {
        for (BoilerState state : BoilerState.values())
            _map.put(state.value, state);
    }

    BoilerState(int value) {
        this.value = value;
    }

    public static BoilerState from(int value)
    {
        return _map.get(value);
    }

    public int getValue() {
        return value;
    }
}
