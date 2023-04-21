package ru.vsu.cs;

import ru.vsu.cs.daos.DaoCsv.BoilerDao;
import ru.vsu.cs.daos.DaoCsv.EnvironmentDao;
import ru.vsu.cs.daos.DaoCsv.EpochTimerDao;
import ru.vsu.cs.daos.DaoCsv.RoomDao;
import ru.vsu.cs.mappers.BoilerMapper;
import ru.vsu.cs.models.*;
import ru.vsu.cs.models.dtos.BoilerDto;
import ru.vsu.cs.services.BoilerService;
import ru.vsu.cs.utils.IdGenerator;

import java.util.*;

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



        BoilerService bs = new BoilerService(boilerDao, new BoilerMapper());
        List<BoilerDto> boilerDaoList = bs.getAll();
        for (var i: boilerDaoList) {
            System.out.println(i.toString());
        }

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