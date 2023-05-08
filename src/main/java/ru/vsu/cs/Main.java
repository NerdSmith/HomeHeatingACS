package ru.vsu.cs;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.EnvironmentRep;
import ru.vsu.cs.models.*;
import ru.vsu.cs.models.dtos.BoilerDto;
import ru.vsu.cs.models.dtos.EnvironmentDto;
import ru.vsu.cs.models.dtos.EpochTimerDto;
import ru.vsu.cs.models.dtos.RoomDto;
import ru.vsu.cs.services.BoilerService;
import ru.vsu.cs.services.EnvironmentService;
import ru.vsu.cs.services.EpochTimerService;
import ru.vsu.cs.services.RoomService;
import ru.vsu.cs.utils.Conn;
import ru.vsu.cs.utils.DependencyInjector;
import ru.vsu.cs.utils.TableCreator;

import java.util.*;


// TODO: servlets
public class Main {

    @Autowired
    private static BoilerService boilerService;
    @Autowired
    private static EnvironmentService environmentService;
    @Autowired
    private static EpochTimerService epochTimerService;
    @Autowired
    private static RoomService roomService;
//
//
    public static void main(String[] args) throws Exception {
        DependencyInjector.inject();
//        GenRep<Boiler> gr = new GenRep<>(Conn.getConn());

        TableCreator.createTable(Conn.getConn(), Environment.class);
        TableCreator.createTable(Conn.getConn(), Boiler.class);
        TableCreator.createTable(Conn.getConn(), EpochTimer.class);
        TableCreator.createTable(Conn.getConn(), Room.class);

//        EnvironmentRep environmentRep = new EnvironmentRep();
////        BoilerRep boilerRep = new BoilerRep();
//        environmentRep.save(new Environment(
//                1,
//                20
//        ));
//        List<Environment> env = environmentRep.getAll();
//        for (var i : env) {
//            System.out.println(i);
//        }
//        System.out.println("------------");
//        environmentRep.delete(env.get(0).getId());
//        env = environmentRep.getAll();
//        for (var i : env) {
//            System.out.println(i);
//        }
//        environmentRep.delete(1);
//        boilerRep.save(new Boiler(
//                1,
//                BoilerState.ON,
//                null
//        ));
//        TableCreator.createTable(Conn.getConn(), EpochTimer.class);
//        TableCreator.createTable(Conn.getConn(), Room.class);
//        gr.create();
////        System.out.println("Hello world!");

        if (environmentService.getAll().equals(new ArrayList<>())) {
            int newEnv = environmentService.saveNew(new EnvironmentDto(0, 30));
            boilerService.saveNew(new BoilerDto(0, 1, newEnv));
            roomService.saveNew(new RoomDto(
                    0,
                    0,
                    100,
                    false,
                    80,
                    50,
                    10,
                    newEnv
                    )
            );
            epochTimerService.saveNew(new EpochTimerDto(
                    0,
                    1000,
                    newEnv
                    )
            );
        }
//
//        System.out.println("Boilers: ");
//        boilerService.getAll().stream().map(JsonSerializer::serialize).forEach(System.out::println);
//        System.out.println("---------");
//        System.out.println("Rooms: ");
//        roomService.getAll().stream().map(JsonSerializer::serialize).forEach(System.out::println);
//        System.out.println("---------");
//        System.out.println("EpochTimers: ");
//        epochTimerService.getAll().stream().map(JsonSerializer::serialize).forEach(System.out::println);
//        System.out.println("---------");
//        System.out.println("Environments: ");
//        environmentService.getAll().stream().map(JsonSerializer::serialize).forEach(System.out::println);
//        System.out.println("---------");
//        IdGenerator idGenerator = IdGenerator.getInstance();
//        Environment environment = new Environment(
//                idGenerator.createID(),
//                15,
//                null,
//                null,
//                new ArrayList<>()
//        );
//        EpochTimer epochTimer = new EpochTimer(
//                idGenerator.createID(),
//                0
//        );
//        Boiler boiler = new Boiler(
//                idGenerator.createID(),
//                BoilerState.OFF
//
//        );
//        Room room1 = new Room(
//                idGenerator.createID(),
//                ValveState.CLOSED,
//                environment.getTemp(),
//                false,
//                23,
//                18,
//                2
//        );
//        environment.setEpochTimer(epochTimer);
//        environment.setBoiler(boiler);
//        environment.addRoom(room1);
//
//
//
//        EpochTimerDao epochTimerDao = new EpochTimerDao();
//        RoomDao roomDao = new RoomDao();
//        BoilerDao boilerDao = new BoilerDao();
//
//        EnvironmentDao environmentDao = new EnvironmentDao();
//        epochTimerDao.save(epochTimer);
//        boilerDao.save(boiler);
//        roomDao.save(room1);
//        environmentDao.save(environment);
//
//
//
//        BoilerService bs = new BoilerService();
//        List<BoilerDto> boilerDaoList = bs.getAll();
//        for (var i: boilerDaoList) {
//            System.out.println(i.toString());
//        }
//
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