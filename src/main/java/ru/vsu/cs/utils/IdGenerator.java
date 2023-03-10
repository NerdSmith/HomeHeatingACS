package ru.vsu.cs.utils;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static IdGenerator INSTANCE;
    private AtomicLong idCounter = new AtomicLong();

    public int createID()
    {
        return (int) idCounter.getAndIncrement();
    }

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new IdGenerator();
        }

        return INSTANCE;
    }
}
