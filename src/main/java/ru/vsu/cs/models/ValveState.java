package ru.vsu.cs.models;

public enum ValveState {
    OPENED(5),
    CLOSED(2),
    HALFOPENED(0);

    private final int value;

    ValveState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
