package ru.vsu.cs.models;

import java.util.HashMap;
import java.util.Map;

public enum ValveState {
    OPENED(5),
    CLOSED(2),
    HALFOPENED(0);

    private final int value;

    private static final Map<Integer, ValveState> _map = new HashMap<>();
    static
    {
        for (ValveState state : ValveState.values())
            _map.put(state.value, state);
    }

    ValveState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ValveState from(int value)
    {
        return _map.get(value);
    }

}
