package ru.vsu.cs;

import ru.vsu.cs.daos.BoilerDao;
import ru.vsu.cs.models.*;
import ru.vsu.cs.utils.IdGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class CustomTimer {
    private int period = 1000;

    public void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.format("%s period %d\n", new Date(), period);
                timer.cancel();
                startTimer();
                period += 0;
            }
        }, period);
    }
}

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello world!");
        IdGenerator idGenerator = IdGenerator.getInstance();
        Environment environment = new Environment(
                idGenerator.createID(),
                15,
                null,
                null,
                new ArrayList<>()
        );
        EpochTimer epochTimer = new EpochTimer(
                idGenerator.createID(),
                0
        );
        Boiler boiler = new Boiler(
                idGenerator.createID(),
                BoilerState.OFF,
                environment
        );
        Room room1 = new Room(
                idGenerator.createID(),
                ValveState.CLOSED,
                environment.getTemp(),
                false,
                23,
                18,
                2,
                environment
        );

        environment.setEpochTimer(epochTimer);
        environment.setBoiler(boiler);
        environment.addRoom(room1);
        System.out.println();

        BoilerDao boilerDao = new BoilerDao();
        boilerDao.save(boiler);
        System.out.println();
    }
}