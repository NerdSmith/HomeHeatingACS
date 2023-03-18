package ru.vsu.cs;

import ru.vsu.cs.daos.DaoCsv.BoilerDao;
import ru.vsu.cs.daos.DaoCsv.EnvironmentDao;
import ru.vsu.cs.daos.DaoCsv.EpochTimerDao;
import ru.vsu.cs.daos.DaoCsv.RoomDao;
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
                BoilerState.OFF

        );
        Room room1 = new Room(
                idGenerator.createID(),
                ValveState.CLOSED,
                environment.getTemp(),
                false,
                23,
                18,
                2
        );
        environment.setEpochTimer(epochTimer);
        environment.setBoiler(boiler);
        environment.addRoom(room1);



        EpochTimerDao epochTimerDao = new EpochTimerDao();
        RoomDao roomDao = new RoomDao();
        BoilerDao boilerDao = new BoilerDao();

        EnvironmentDao environmentDao = new EnvironmentDao(
                epochTimerDao,
                boilerDao,
                roomDao
        );
        epochTimerDao.save(epochTimer);
        boilerDao.save(boiler);
        roomDao.save(room1);
        environmentDao.save(environment);


//        environment.setEpochTimer(epochTimer);
//        environment.setBoiler(boiler);
//        environment.addRoom(room1);
//        System.out.println();
//
//        BoilerDao boilerDao = new BoilerDao();
//        boilerDao.save(boiler);
//        System.out.println();
//        var r = boilerDao.getAll();
//        var r2 = boilerDao.get(2);
//        System.out.println();
//
//        var r5 = r2.get();
//        r5.setBoilerState(BoilerState.ON);
//        boilerDao.update(r5);
//
//        var r7 = boilerDao.get(2);
//
//        boilerDao.delete(r2.get());
//        var r3 = boilerDao.get(2);
//        RoomDao roomDao = new RoomDao();
//        roomDao.save(room1);
//        Room r = roomDao.get(room1.getId()).get();
//        EnvironmentDao environmentDao = new EnvironmentDao(
//                new EpochTimerDao(),
//                new BoilerDao(),
//                new RoomDao()
//        );
//        environmentDao.save(environment);

        System.out.println();
    }
}